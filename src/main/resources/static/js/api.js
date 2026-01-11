// API Helper Functions
const API = {
    baseURL: '',
    
    getToken() {
        return localStorage.getItem('accessToken');
    },
    
    setToken(token) {
        localStorage.setItem('accessToken', token);
    },
    
    getRefreshToken() {
        return localStorage.getItem('refreshToken');
    },
    
    setRefreshToken(token) {
        localStorage.setItem('refreshToken', token);
    },
    
    getUserEmail() {
        return localStorage.getItem('userEmail');
    },
    
    setUserEmail(email) {
        localStorage.setItem('userEmail', email);
    },
    
    clearAuth() {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('userEmail');
    },
    
    isAuthenticated() {
        return !!this.getToken();
    },
    
    async request(endpoint, options = {}) {
        const token = this.getToken();
        const headers = {
            'Content-Type': 'application/json',
            ...options.headers
        };
        
        if (token && !options.skipAuth) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        
        const config = {
            ...options,
            headers
        };
        
        try {
            const response = await fetch(this.baseURL + endpoint, config);
            
            // Handle 401 - try to refresh token
            if (response.status === 401 && !options.skipAuth) {
                const refreshed = await this.refreshAccessToken();
                if (refreshed) {
                    // Retry original request
                    headers['Authorization'] = `Bearer ${this.getToken()}`;
                    const retryResponse = await fetch(this.baseURL + endpoint, config);
                    return this.handleResponse(retryResponse);
                }
            }
            
            return this.handleResponse(response);
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    },
    
    async handleResponse(response) {
        const contentType = response.headers.get('content-type');
        let data;
        
        if (contentType && contentType.includes('application/json')) {
            data = await response.json();
        } else {
            data = await response.text();
        }
        
        if (!response.ok) {
            throw {
                status: response.status,
                message: typeof data === 'string' ? data : data.message || 'Request failed',
                data: data
            };
        }
        
        return data;
    },
    
    async refreshAccessToken() {
        const refreshToken = this.getRefreshToken();
        if (!refreshToken) return false;
        
        try {
            const response = await fetch(this.baseURL + '/auth/refresh', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ refreshToken })
            });
            
            if (response.ok) {
                const data = await response.json();
                this.setToken(data.accessToken);
                return true;
            }
        } catch (error) {
            console.error('Token refresh failed:', error);
        }
        
        this.clearAuth();
        return false;
    },
    
    // Auth endpoints
    async register(email, password) {
        return this.request('/users/register', {
            method: 'POST',
            body: JSON.stringify({ email, password }),
            skipAuth: true
        });
    },
    
    async login(email, password) {
        const data = await this.request('/users/login', {
            method: 'POST',
            body: JSON.stringify({ email, password }),
            skipAuth: true
        });
        
        this.setToken(data.accessToken);
        this.setRefreshToken(data.refreshToken);
        this.setUserEmail(data.email);
        
        return data;
    },
    
    logout() {
        this.clearAuth();
        window.location.href = '/auth';
    },
    
    // User endpoints
    async getUser(id) {
        return this.request(`/users/${id}`);
    },
    
    async updateUserEmail(id, email) {
        return this.request(`/users/${id}/email`, {
            method: 'PUT',
            body: JSON.stringify({ email })
        });
    },
    
    async updateUserPassword(id, password) {
        return this.request(`/users/${id}/password`, {
            method: 'PUT',
            body: JSON.stringify({ password })
        });
    },
    
    async deleteUser(id) {
        return this.request(`/users/${id}`, {
            method: 'DELETE'
        });
    },
    
    // Property endpoints
    async getProperties() {
        return this.request('/properties');
    },
    
    async getProperty(amaNumber) {
        return this.request(`/properties/${amaNumber}`);
    },
    
    async searchProperties(params) {
        const query = new URLSearchParams(params).toString();
        return this.request(`/properties/specs?${query}`);
    },
    
    async addProperty(data) {
        return this.request('/properties', {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },
    
    async updateProperty(amaNumber, data) {
        return this.request(`/properties/${amaNumber}`, {
            method: 'PATCH',
            body: JSON.stringify(data)
        });
    },
    
    async deleteProperty(amaNumber) {
        return this.request(`/properties/${amaNumber}`, {
            method: 'DELETE'
        });
    },
    
    // Booking endpoints
    async getBookings(userId) {
        return this.request(`/bookings/${userId}`);
    },
    
    async createBooking(userId, propertyId, data) {
        return this.request(`/bookings/${userId}/${propertyId}`, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },
    
    async updateBookingStatus(id, status) {
        return this.request(`/bookings/${id}`, {
            method: 'PATCH',
            body: JSON.stringify({ status })
        });
    },
    
    // Amenity endpoints
    async addAmenity(name) {
        return this.request('/amenities', {
            method: 'POST',
            body: JSON.stringify({ name })
        });
    },
    
    async addPropertyAmenity(propertyId, amenityId, quantity) {
        return this.request(`/properties/${propertyId}/amenities/${amenityId}`, {
            method: 'POST',
            body: JSON.stringify({ quantity })
        });
    },
    
    // Role endpoints
    async getRoles() {
        return this.request('/roles');
    },
    
    async addRole(name) {
        return this.request('/roles', {
            method: 'POST',
            body: JSON.stringify({ name })
        });
    }
};

// UI Helper Functions
const UI = {
    showSuccess(message) {
        this.showAlert(message, 'success');
    },
    
    showError(error) {
        const message = error.message || error.toString();
        this.showAlert(message, 'error');
    },
    
    showAlert(message, type = 'info') {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        alertDiv.textContent = message;
        
        const container = document.querySelector('.container');
        container.insertBefore(alertDiv, container.firstChild);
        
        setTimeout(() => alertDiv.remove(), 5000);
    },
    
    formatDate(dateString) {
        if (!dateString) return 'N/A';
        return new Date(dateString).toLocaleString();
    },
    
    formatCurrency(amount) {
        if (!amount) return '€0.00';
        return `€${parseFloat(amount).toFixed(2)}`;
    },
    
    formatJSON(obj) {
        return JSON.stringify(obj, null, 2);
    },
    
    updateAuthStatus() {
        const authStatusEl = document.getElementById('authStatus');
        if (!authStatusEl) return;
        
        if (API.isAuthenticated()) {
            const email = API.getUserEmail();
            authStatusEl.innerHTML = `
                Logged in as: <strong>${email}</strong> | 
                <a href="#" onclick="API.logout(); return false;">Logout</a>
            `;
        } else {
            authStatusEl.innerHTML = `
                <a href="/auth">Login / Register</a>
            `;
        }
    },
    
    requireAuth() {
        if (!API.isAuthenticated()) {
            window.location.href = '/auth';
            return false;
        }
        return true;
    }
};

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    UI.updateAuthStatus();
});
