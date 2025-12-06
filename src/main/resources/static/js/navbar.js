document.addEventListener('DOMContentLoaded', () => {
    const authOnly = document.querySelectorAll('.nav-auth-only');
    const guestOnly = document.querySelectorAll('.nav-guest-only');
    const logoutBtn = document.getElementById('nav-logout');

    let isAuth = false;

    try {
        isAuth = !!(window.Auth && Auth.token);
    } catch (e) {
        isAuth = false;
    }

    authOnly.forEach(el => {
        if (isAuth) {
            el.style.setProperty('display', 'inline-flex', 'important');
            el.style.setProperty('flex-direction', 'column', 'important');
        } else {
            el.style.setProperty('display', 'none', 'important');
        }
    });

    guestOnly.forEach(el => {
        if (isAuth) {
            el.style.setProperty('display', 'none', 'important');
        } else {
            el.style.setProperty('display', 'inline-flex', 'important');
            el.style.setProperty('flex-direction', 'column', 'important');
        }
    });

    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            if (window.Auth && typeof Auth.logout === 'function') {
                Auth.logout();
            } else {
                localStorage.clear();
                window.location.href = '/login';
            }
        });
    }
});