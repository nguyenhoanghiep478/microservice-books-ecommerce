import { useAuth } from "@/app/context/AuthContext";
import { Permission } from "@/type/permission.dt";
import { useRouter } from "next/navigation";

import { useEffect, useState } from "react";
import { Button, Container, Nav, Navbar, NavDropdown } from "react-bootstrap";

const AppHeaderAdmin = () =>{
    const { isAuthenticated, setIsAuthenticated } = useAuth();
    const [permissions, setPermissions] = useState<Permission[]>([]);
    useEffect(() => {
        const storedPermissions = JSON.parse(localStorage.getItem('permission') || '[]') as Permission[];
        setPermissions(storedPermissions);
    }, []);

    const route = useRouter()
    const handleLoginClick = () => {
        route.push("/login");
    }


    const handleHomePageClick = () => {
        route.push("/")
    }

    const handleProductPageClick = () => {
        route.push("/admin/dashboard/product")
    }

    const handleLogoutClick = () => {
        setIsAuthenticated(false);
        localStorage.removeItem('token');
        localStorage.removeItem('permission');
    }

    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container>
                    <Navbar.Brand href="#home">Admin Dashboard</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="#home" onClick={handleHomePageClick}>Home</Nav.Link>
                        </Nav>
                        {permissions.length > 0 && (
                            <NavDropdown title="Admin Page" id="basic-nav-dropdown" align="start" className="btn btn-outline-success ms-3">
                                {permissions.includes(Permission.ACCOUNTING_MANAGER) && (
                                    <NavDropdown.Item>
                                        <Button variant="outline-primary" onClick={handleProductPageClick}>Product Page</Button>
                                    </NavDropdown.Item>
                                )}
                                {permissions.includes(Permission.MARKETING_MANAGER) && (
                                    <NavDropdown.Item>
                                        <Button variant="outline-primary" >Marketing Page</Button>
                                    </NavDropdown.Item>
                                )}
                                {permissions.includes(Permission.ACCOUNTING_MANAGER) && (
                                    <NavDropdown.Item >
                                        <Button variant="outline-primary" >Accounting Page</Button>
                                    </NavDropdown.Item>
                                )}
                                {permissions.includes(Permission.LANDINGPAGE_MANAGER) && (
                                    <NavDropdown.Item >
                                        <Button variant="outline-primary" >Landing Page</Button>
                                    </NavDropdown.Item>
                                )}
                            </NavDropdown>
                        )}
                       
                        {isAuthenticated ? (
                            <Button variant="outline-danger" className="ms-3" onClick={handleLogoutClick}>Logout</Button>
                        ) : (
                            <Button variant="outline-primary" className="ms-3" onClick={handleLoginClick}>Login</Button>
                        )
                        }
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    )}

export default AppHeaderAdmin;