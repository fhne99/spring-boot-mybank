function stocksApp() {
    return {
        stocks: [],
        loading: false,
        error: null,
        submitting: false,
        newStock: {
            symbol: '',
            companyName: '',
            priceCents: null,
            currency: 'EUR'
        },

        async init() {
            await this.fetchStocks();
        },

        async fetchStocks() {
            this.loading = true;
            this.error = null;

            try {
                const response = await fetch('/api/stocks', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${Auth.token}`  // <- token JWT ajouté
                    },
                    credentials: 'include'
                });

                if (!response.ok) {
                    if (response.status === 401 || response.status === 403) {
                        window.location.href = '/login';
                        return;
                    }
                    throw new Error(`Erreur HTTP ${response.status}`);
                }

                this.stocks = await response.json();
            } catch (err) {
                console.error('Error fetching stocks:', err);
                this.error = '❌ Impossible de charger les cotations. Veuillez réessayer.';
            } finally {
                this.loading = false;
            }
        },

        async createStock() {
            if (!this.newStock.symbol || !this.newStock.companyName ||
                this.newStock.priceCents === null || !this.newStock.currency) {
                this.error = '⚠️ Veuillez remplir tous les champs';
                return;
            }

            this.submitting = true;
            this.error = null;

            try {
                const response = await fetch('/api/stocks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${Auth.token}` // <- token JWT ajouté
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        symbol: this.newStock.symbol.toUpperCase(),
                        companyName: this.newStock.companyName,
                        priceCents: parseInt(this.newStock.priceCents),
                        currency: this.newStock.currency
                    })
                });

                if (!response.ok) {
                    if (response.status === 401 || response.status === 403) {
                        window.location.href = '/login';
                        return;
                    }
                    const errorData = await response.json();
                    throw new Error(errorData.message || `Erreur HTTP ${response.status}`);
                }

                // Reset du formulaire
                this.newStock = {
                    symbol: '',
                    companyName: '',
                    priceCents: null,
                    currency: 'EUR'
                };

                // Rechargement des stocks
                await this.fetchStocks();
            } catch (err) {
                console.error('Error creating stock:', err);
                this.error = `❌ ${err.message || 'Impossible de créer la cotation. Veuillez réessayer.'}`;
            } finally {
                this.submitting = false;
            }
        },

        formatPrice(amount, currency) {
            const currencySymbols = {
                'EUR': '€',
                'USD': '$',
                'GBP': '£',
                'JPY': '¥',
                'CHF': 'CHF'
            };

            return new Intl.NumberFormat('fr-FR', {
                style: 'decimal',
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            }).format(amount) + ' ' + (currencySymbols[currency] || currency);
        },

        formatDate(timestamp) {
            return new Intl.DateTimeFormat('fr-FR', {
                dateStyle: 'short',
                timeStyle: 'medium'
            }).format(new Date(timestamp));
        }
    };
}
