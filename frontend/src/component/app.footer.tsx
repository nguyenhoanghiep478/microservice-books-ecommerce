// components/AppFooter.js
import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

function AppFooter() {
  return (
    <footer className="bg-light text-center text-lg-start" style={{marginTop: '100px'}}>
      <Container className="p-4">
        <Row>
          {/* Link Column */}
          <Col lg={6} md={12} className="mb-4 mb-md-0">
            <h5 className="text-uppercase">Liên kết</h5>
            <ul className="list-unstyled mb-0">
              <li><a href="#home" className="text-dark">Home</a></li>
              <li><a href="#about" className="text-dark">About</a></li>
              <li><a href="#services" className="text-dark">Services</a></li>
              <li><a href="#contact" className="text-dark">Contact</a></li>
            </ul>
          </Col>

          {/* Contact Info Column */}
          <Col lg={6} md={12} className="mb-4 mb-md-0">
            <h5 className="text-uppercase">Thông tin liên hệ</h5>
            <p>123 Đường ABC, Quận XYZ, Thành phố Hồ Chí Minh</p>
            <p>Email: info@example.com</p>
            <p>Điện thoại: +84 123 456 789</p>
          </Col>
        </Row>
      </Container>

      {/* Footer Bottom */}
      <div className="footer-bottom text-center">
        © 2024 Bản quyền thuộc về: <a className="text-white" href="https://yourwebsite.com/">YourWebsite.com</a>
      </div>
    </footer>
  );
}

export default AppFooter;
