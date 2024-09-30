'use client'

import { useAuth } from "@/app/context/AuthContext";
import { logout } from "@/app/state/logout";
import { Permission} from "@/type/permission.dt";
import { useRouter } from "next/navigation"
import { useEffect, useState } from "react";
import { Container, Nav, Navbar, NavDropdown, Form, FormControl, Button } from "react-bootstrap"
import styles from '@/app/ui/client/navbar/navbar.module.css'
import { FaShoppingCart } from 'react-icons/fa'; 

const AppHeader = () => {
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

    const handleCartClick = () =>{
        route.push("/cart");
    }

    const handleHomePageClick = () => {
        route.push("/")
    }

    const handleDashboardClick = () => {
        route.push("/dashboard")
    }


    return (
        <>
            <Navbar expand="lg" className={`bg-body-tertiary ${styles.container}`} >
                <Container>
                    <Navbar.Brand href="#home" className={styles.title}>BookStore</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="#home" onClick={handleHomePageClick}>Home</Nav.Link>
                            <Nav.Link href="#link">Link</Nav.Link>
                            <NavDropdown title="Dropdown" id="basic-nav-dropdown" align="end">
                                <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.2">
                                    Another action
                                </NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                                <NavDropdown.Divider />
                                <NavDropdown.Item href="#action/3.4">
                                    Separated link
                                </NavDropdown.Item>
                            </NavDropdown>

                        </Nav>
                        <Form className="d-flex ms-auto">
                            <FormControl
                                type="search"
                                placeholder="Search"
                                className="me-2"
                                aria-label="Search"
                            />
                            <Button variant="outline-success">Search</Button>
                        </Form>
                        {permissions.length > 0 && (
                            <NavDropdown title="Admin Page" id="basic-nav-dropdown" align="start" className="btn btn-outline-success ms-3">
                                <NavDropdown.Item>
                                        <Button variant="outline-primary" onClick={handleDashboardClick}>Admin page</Button>
                                    </NavDropdown.Item>
                            </NavDropdown>
                        )}
                        <Button variant="outline-primary" className="ms-3" onClick={handleCartClick}><FaShoppingCart /> Cart</Button>

                        {isAuthenticated ? (
                            <Button variant="outline-danger" className="ms-3" onClick={() => logout(setIsAuthenticated,route)}>Logout</Button>
                        ) : (
                            <Button variant="outline-primary" className="ms-3" onClick={handleLoginClick}>Login</Button>
                        )
                        }
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    )
}
export default AppHeader;