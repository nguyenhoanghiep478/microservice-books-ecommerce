

export const logout = (setIsAuthenticated, router) => {
    if(typeof window !== 'undefined'){
        setIsAuthenticated(false)
        localStorage.removeItem('token'),
        localStorage.removeItem('permission');
    }

    router.push("/")
}