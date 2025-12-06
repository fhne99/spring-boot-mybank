function accountsData(clientId) {
    return {
        clientId: clientId,
        accounts: [],
        newAccount: { name: '', type: 'COURANT', balance: '' },
        loading: false,
        saving: false,

        mapType(type) {
            const types = {
                "COURANT": "Courant",
                "LIVRET_A": "Livret A",
                "PEA": "PEA"
            };
            return types[type] || type;
        },

        formatEuros(amount) {
            return amount.toFixed(2) + " €";
        },

        init() {
            this.fetchAccounts();
        },

        fetchAccounts() {
            if (Auth.token) headers['Authorization'] = `Bearer ${Auth.token}`;
            fetch('/api/clients/accounts', {
                headers: headers
            })
                .then(data => {
                    console.log(data)
                    this.accounts = data;
                    this.loading = false;
                })
                .catch(e => {
                    this.error = e.message;
                    this.loading = false;
                });
        },

        createAccount() {
            this.saving = true;
            const headers = {'Content-Type':'application/json'};
            if (Auth.token) headers['Authorization'] = `Bearer ${Auth.token}`;
            fetch('/api/clients/accounts', {
                method: 'POST',
                headers: headers,
                body: JSON.stringify(
                    {
                        name: this.newAccount.name,
                        type: this.newAccount.type,
                        amountCents: Number(this.newAccount.amount * 100)
                    }
                )
            })
                .then(() => {
                    this.loadAccounts();
                    this.newAccount = {name: '', type: 'COMPTE_COURANT', amount: 0};
                    this.saving = false;
                });
        }
    }
}

const Auth = {
    get token() {
        return localStorage.getItem('jwt');
    },
    set token(t) {
        if (t) localStorage.setItem('jwt', t); else localStorage.removeItem('jwt');
    },
    get clientId() {
        return localStorage.getItem('clientId');
    },
    set clientId(c) {
        if (c) localStorage.setItem('clientId', c); else localStorage.removeItem('clientId');
    },
    get login() {
        return localStorage.getItem('login');
    },
    set login(l) {
        if (l) localStorage.setItem('login', l); else localStorage.removeItem('login');
    },
    logout() {
        try {
            localStorage.removeItem('jwt');
            localStorage.removeItem('clientId');
            localStorage.removeItem('login');
        } catch (_) {
        }
        window.location.href = '/login';
    }
};