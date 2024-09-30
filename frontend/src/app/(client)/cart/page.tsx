'use client'

import { useState, useEffect, ChangeEvent, FormEvent } from "react";
import { Button, Container, Form, Table, Spinner } from "react-bootstrap";
import jwt, { JwtPayload } from 'jsonwebtoken';
import { OrderItem } from "@/type/dto/order-item.dt";
import { OrderType } from "@/type/order-type.dt";
import { Order } from "@/type/dto/order.dt";
import { Status } from "@/type/status.dt";
import styles from "@/app/ui/client/cart/cart.module.css"
import { FaPaypal, FaTrash } from 'react-icons/fa';

const OrderUrl = "http://localhost:8031/api/v1/order/pay-order";

const CartPage = () => {
    const [cart, setCart] = useState<OrderItem[]>([]);
    const [orderItems, setOrderItems] = useState<Set<number>>(new Set());
    const [loading, setLoading] = useState(false);
    const [emailToken, setEmailToken] = useState<number>(0);
    const [inputToken, setInputToken] = useState<string[]>(Array(6).fill(''));
    const [showTokenInput, setShowTokenInput] = useState<boolean>(false);
    const [orderNumber, setOrderNumber] = useState<number>(0);


    useEffect(() => {
        const cartJson = localStorage.getItem('cart');
        if (cartJson) {
            const cartItems: OrderItem[] = JSON.parse(cartJson);
            cartItems.sort((a, b) => a.bookId - b.bookId);
            setCart(cartItems);
        }
    }, []);

    const handleRemoveItem = (bookId: number) => {
        const updatedCart = cart.filter(item => item.bookId !== bookId);
        setCart(updatedCart);
        localStorage.setItem('cart', JSON.stringify(updatedCart));
    };

    const handleCheckboxChange = (bookId: number, checked: boolean) => {
        const updatedOrderItems = new Set(orderItems);
        if (checked) {
            updatedOrderItems.add(bookId);
        } else {
            updatedOrderItems.delete(bookId);
        }
        setOrderItems(updatedOrderItems);
    };

    const handleRemoveSelected = () => {
        const updatedCart = cart.filter(item => !orderItems.has(item.bookId));
        setCart(updatedCart);
        setOrderItems(new Set());
        localStorage.setItem('cart', JSON.stringify(updatedCart));
    };

    function getOrderNumber(): number {
        if (!showTokenInput) {
            const generateNumber = Math.floor(Math.random() * (1000000 - 1 + 1)) + 1
            setOrderNumber(generateNumber)
            return generateNumber;
        }
        return orderNumber;
    }

    const selectedItems = cart.filter(item => orderItems.has(item.bookId));
    const totalAmount = selectedItems.reduce((acc, item) => acc + (item.price * item.totalQuantity), 0);
    const handleBuyClick = async () => {
        setLoading(true);
        const token = localStorage.getItem('token');
        const payload = jwt.decode(token as string) as JwtPayload;
        const customerId = parseInt(payload.id as string, 10);
        const result = inputToken.join('')
        const verifyToken = result === '' ? null : result;
        const order = new Order(getOrderNumber(), OrderType.SELL, Status.PENDING, customerId, '', parseFloat(totalAmount.toFixed(2)), selectedItems, "Paypal", '', verifyToken);

        try {
            const response = await fetch(OrderUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(order)
            });

            const data = await response.json();
            if (data.result === null) {
                setShowTokenInput(true); // Hiển thị ô nhập mã token nếu message là null
            } else {
                const updatedCart = cart.filter(item => !orderItems.has(item.bookId));
                window.location.href = data.result.message;
                localStorage.setItem('cart', JSON.stringify(updatedCart));
            }
        } catch (e) {
            console.error(e);
        } finally {
            setLoading(false); // Kết thúc trạng thái loading
        }
    };

    const handleTokenChange = (index: number, event: ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value.replace(/\D/g, '');
        const newToken = [...inputToken];
        newToken[index] = value;
        setInputToken(newToken);
    };

    const handleTokenSubmit = (event: FormEvent) => {
        event.preventDefault();
        const tokenValue = inputToken.join('');
        setEmailToken(parseInt(tokenValue, 10));
        setShowTokenInput(false); // Ẩn ô nhập token sau khi xác thực
        handleBuyClick();
    };

    const handleBackToCart = () => {
        setShowTokenInput(false);
    };

    return (
        <Container className={styles.container}>
            {showTokenInput ? (
                <div className="token-container">
                   <div className={styles.backButton}>
                   <Button variant="secondary" onClick={handleBackToCart} className="mt-3 ml-2">
                        Back to Cart
                    </Button>
                   </div>
                    <h2 className='d-flex justify-content-center'>Enter Token</h2>
                    <span className={styles.message}>We sent token to your email, please check it and fill token to keep transaction</span>
                    <Form onSubmit={handleTokenSubmit} className={styles.tokenForm}>
                        <div className={styles.inputForm}>
                            {inputToken.map((value, index) => (
                                <Form.Control
                                    key={index}
                                    type="text"
                                    value={value}
                                    onChange={(e) => handleTokenChange(index, e)}
                                    maxLength={1}
                                    className={styles.tokenInput}
                                />
                            ))}
                        </div>
                        <Button variant="primary" type="submit" className="mt-3">
                            Submit
                        </Button>

                    </Form>
                </div>
            ) : (
                <div>
                    <h2 className='d-flex justify-content-center'>Cart</h2>
                    {selectedItems.length > 0 && (
                        <Button variant="danger" onClick={handleRemoveSelected} className="mb-3">
                            Remove Selected
                        </Button>
                    )}
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {cart.map(item => (
                                <tr key={item.bookId}>
                                    <td>
                                        <Form.Check
                                            type="checkbox"
                                            checked={orderItems.has(item.bookId)}
                                            onChange={(e) => handleCheckboxChange(item.bookId, e.target.checked)}
                                        />
                                    </td>
                                    <td>{item.name}</td>
                                    <td>${item.price.toFixed(2)}</td>
                                    <td>{item.totalQuantity}</td>
                                    <td>${(item.price * item.totalQuantity).toFixed(2)}</td>
                                    <td>
                                        <Button variant="danger" onClick={() => handleRemoveItem(item.bookId)}>
                                            <FaTrash/>
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>

                    <div className="mt-4">
                        <h3 className='d-flex justify-content-center'>Order Summary</h3>
                        {selectedItems.length > 0 ? (
                            <div>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {selectedItems.map(item => (
                                            <tr key={item.bookId}>
                                                <td>{item.name}</td>
                                                <td>${item.price.toFixed(2)}</td>
                                                <td>{item.totalQuantity}</td>
                                                <td>${(item.price * item.totalQuantity).toFixed(2)}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </Table>
                                <div className="d-flex justify-content-end">
                                    <h4>Total Amount: ${totalAmount.toFixed(2)}</h4>
                                </div>
                                <div className='d-flex justify-content-center'>
                                    <Button variant="primary" onClick={handleBuyClick}>
                                        <FaPaypal/>
                                        Pay with Paypal
                                    </Button>
                                </div>
                            </div>
                        ) : (
                            <p className='d-flex justify-content-center'>No items selected.</p>
                        )}
                    </div>

                    {/* Loading spinner */}
                    {loading && (
                        <div className="loading-overlay">
                            <Spinner animation="border" role="status">
                                <span className="visually-hidden">Loading...</span>
                            </Spinner>
                        </div>
                    )}
                </div>
            )}

            <style jsx>{`
                .loading-overlay {
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background: rgba(0, 0, 0, 0.5);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    z-index: 9999;
                }
                .token-container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    height: 100vh;
                }
            `}</style>
        </Container>
    );
};

export default CartPage;
