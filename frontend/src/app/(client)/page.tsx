'use client'
import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Carousel, Button, Pagination, Spinner, Modal } from 'react-bootstrap';
import { CSSTransition, TransitionGroup } from 'react-transition-group';

import styles from '@/app/ui/client/home/home.module.css';
import useSWR from 'swr';
import { OrderItem } from '@/type/dto/order-item.dt';



const fetcher = (url: string) => fetch(url).then((res) => res.json());
const containerStyle = {
  minHeight: '700px',
};

export default function Home() {
  const [currentPage, setCurrentPage] = useState(1);
  const [products, setProducts] = useState<Product[]>([]);
  const [totalPages, setTotalPages] = useState(1);
  const [showModal, setShowModal] = useState(false);
  const [selectedImage, setSelectedImage] = useState('');
  const [animatedImage, setAnimatedImage] = useState<string | null>(null);
  const [isAnimating, setIsAnimating] = useState(false);


  const { data, error } = useSWR('http://localhost:8031/api/v1/book/anonymous/get-all', fetcher);
  const topSales = useSWR('http://localhost:8031/api/v1/book/anonymous/get-top-sales',fetcher);

  const itemsPerPage = 6;
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;

  const handleImageClick = (target:string) => {
    setSelectedImage(target);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };



  useEffect(() => {
    if (data) {
      if (data.error == null) {
        setProducts(data.result);
        setTotalPages(Math.ceil(data?.result.length / itemsPerPage));
      }
    }
  }, [data]);

  if (error) {
    return (
      <Container className="text-center">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
        <div>Failed to load products</div>
      </Container>
    );
  }

  if (data?.code != null || topSales?.data == null) return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Spinner animation="border" role="status">
        <span className="visually-hidden">Loading...</span>
      </Spinner>
    </Container>
  );



  const currentProducts:Product[] = products.slice(startIndex, endIndex);
  const productRows = [];
  for (let i = 0; i < currentProducts.length; i += 3) {
    productRows.push(currentProducts.slice(i, i + 3));
  }

  
  const topSalesProduct:Product[] = topSales.data.result;
  const slides:Product[] = [];
  for (let i = 0; i < topSalesProduct.length; i += 3) {
    slides.push(topSalesProduct.slice(i, i + 3));
  }
  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };


  const handleAddToCardClick = (target:Product) => {
    let cartJson = localStorage.getItem('cart')
    if (!cartJson) {
      localStorage.setItem('cart', JSON.stringify([]));
      cartJson = localStorage.getItem('cart');
    }
    let cart: OrderItem[] = JSON.parse(cartJson as string).map((item: any) => Object.assign(new OrderItem(item.bookId, item.name, item.price), item));
    let order = new OrderItem(target.id,target.name,target.price);
    let orderInCart = cart.find(order => order.bookId === target.id) as OrderItem;

    if(orderInCart){
      orderInCart.addTotalQuantity();
    }else{
      cart.push(order);
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    
    setAnimatedImage(target.name);
    setIsAnimating(true);
    
    setTimeout(() => {
      setIsAnimating(false);
      setAnimatedImage(null);
    }, 2000);  

  }

  const getAnimationByIndex = (index) => {
    console.log(index)
    if(index %2 == 0 && index > 0){
      return styles.flyFromLast
    }else{
      return styles.flyToCart
    }
  }

  const pageButtons = [];

  if (totalPages > 1) {
    if (currentPage > 1 && currentPage < totalPages - 1) {
      // Case 1: currentPage > 1 and < totalPages - 1
      pageButtons.push(
        <Pagination.First onClick={() => handlePageChange(1)} />
      );
      pageButtons.push(
        <Pagination.Prev key="prev" onClick={() => handlePageChange(currentPage - 1)} />
      );
      pageButtons.push(
        <Pagination.Item key={currentPage}>{currentPage}</Pagination.Item>
      );
      pageButtons.push(
        <Pagination.Ellipsis />
      );
      pageButtons.push(
        <Pagination.Item key={currentPage + 1} onClick={() => handlePageChange(currentPage + 1)}>
          {currentPage + 1}
        </Pagination.Item>
      );
      pageButtons.push(
        <Pagination.Next key="next" onClick={() => handlePageChange(currentPage + 1)} />
      );
      pageButtons.push(
        <Pagination.Last onClick={() => handlePageChange(totalPages)} />
      );
    } else if (currentPage <= 1) {
      // Case 2: currentPage <= 1
      pageButtons.push(
        <Pagination.Item key={1} onClick={() => handlePageChange(1)}>
          {1}
        </Pagination.Item>
      );
      pageButtons.push(
        <Pagination.Item key={2} onClick={() => handlePageChange(2)}>
          {2}
        </Pagination.Item>
      );
      if (totalPages > 2) {
        pageButtons.push(
          <Pagination.Item key={3} onClick={() => handlePageChange(3)}>
            {3}
          </Pagination.Item>
        );
      }
      pageButtons.push(
        <Pagination.Next key="next" onClick={() => handlePageChange(currentPage + 1)} />
      );
    } else if (currentPage >= totalPages - 1) {
      pageButtons.push(
        <Pagination.Prev key="prev" onClick={() => handlePageChange(currentPage - 1)} />
      );
      if (totalPages > 2) {
        pageButtons.push(
          <Pagination.Item key={totalPages - 2} onClick={() => handlePageChange(totalPages - 2)}>
            {totalPages - 2}
          </Pagination.Item>
        );
      }
      if (totalPages > 1) {
        pageButtons.push(
          <Pagination.Item key={totalPages - 1} onClick={() => handlePageChange(totalPages - 1)}>
            {totalPages - 1}
          </Pagination.Item>
        );
      }
      pageButtons.push(
        <Pagination.Item key={totalPages} active>
          {totalPages}
        </Pagination.Item>
      );
    }
  }

  return (
    <Container style={containerStyle} className="d-flex flex-column justify-content-center mt-5">
       <h1 className='d-flex justify-content-center align-items-center mt-5'>Top Sales</h1>
      <Carousel className="mt-5">
       
        {slides.map((slide, index) => (
          <Carousel.Item key={index}>
            <Row className="d-flex justify-content-center align-items-center flex-grow-1">
              {slide.map((product, idx) => (
                <Col key={product.id} md={4} className="mb-4">
                  <Card className="d-flex align-items-center">
                    <Card.Img  variant="top"
                        src={`data:image/jpeg;base64,${product.image}`}
                        className={`${styles.cardImg} ${animatedImage === product.name && isAnimating ? getAnimationByIndex(idx) : ''}`}
                        onClick={() => handleImageClick(product.image)} />
                    <Card.Body>
                      <Card.Title className='d-flex justify-content-center'>{product.name}</Card.Title>
                      <Card.Text className='d-flex justify-content-center'>{product.price}$</Card.Text>
                      <div className='d-flex justify-content-center'>
                        <Button className={styles.cardButton} variant="primary">View Detail</Button>
                        <Button className={styles.cardButton} variant="primary">Buy Now</Button>
                        <Button className={styles.cardButton} variant="primary" onClick={() => handleAddToCardClick(product)}>Add to Cart</Button>
                      </div>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
          </Carousel.Item>
        ))}
      </Carousel>

      <Row className="d-flex justify-content-center align-items-center flex-grow-1 mt-5">
      <h1 className='d-flex justify-content-center align-items-center'>Books</h1>
        <TransitionGroup>
          {productRows.map((row, rowIndex) => (
            <CSSTransition
              key={rowIndex}
              timeout={500}
              classNames="fade"
            >
              <Row className="d-flex justify-content-center align-items-center flex-grow-1 mt-3">
                {row.length ? row.map((product,productIndex) => (
                  
                  <Col key={product.id} md={4} className="mb-4">
                    <Card className="d-flex align-items-center">
                      <Card.Img  variant="top"
                        src={`data:image/jpeg;base64,${product.image}`}
                        className={`${styles.cardImg} ${animatedImage === product.name && isAnimating ? getAnimationByIndex(productIndex) : ''}`}
                        onClick={() => handleImageClick(product.image)} />
                      <Card.Body>
                        <Card.Title className='d-flex justify-content-center'>{product.name}</Card.Title>
                        <Card.Text className='d-flex justify-content-center'>{product.price}$</Card.Text>
                        <div className='d-flex justify-content-center'>
                          <Button className={styles.cardButton} variant="primary">View Detail</Button>
                          <Button className={styles.cardButton} variant="primary">Buy Now</Button>
                          <Button className={styles.cardButton} variant="primary" onClick={() => handleAddToCardClick(product)}>Add to Cart</Button>
                        </div>
                      </Card.Body>
                    </Card>
                  </Col>
                )) : (
                  <Col md={4} className="mb-4">
                    <Card>
                      <Card.Body>
                        <Card.Title>No Product</Card.Title>
                        <Card.Text>No products available.</Card.Text>
                      </Card.Body>
                    </Card>
                  </Col>
                )}
              </Row>
            </CSSTransition>
          ))}
        </TransitionGroup>
      </Row>

      <div className="mt-5">
        <Pagination className="justify-content-center">
          {pageButtons}
        </Pagination>
      </div>
      <Modal show={showModal} onHide={handleCloseModal} centered>
        <Modal.Body className="text-center">
          <img
            src={`data:image/jpeg;base64,${selectedImage}`}
            alt="Product"
            style={{ maxWidth: '100%', maxHeight: '100%' }}
          />
        </Modal.Body>
      </Modal>
    </Container>
  );
}
