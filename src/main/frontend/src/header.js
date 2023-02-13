import { useState } from "react";
import { Button,Navbar,Offcanvas,Form,Container,Nav} from "react-bootstrap";
import { Link } from "react-router-dom";

function Header(){

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
  
    return (
        <Navbar bg="light" expand="lg" style={{height :'15vh', textAlign: 'center', display: 'flex', alignItems:'center',boxShadow: '1px 1px 3px 1px #44194C'}}>
            <Container fluid style={{justifyContent:'around' }}>
                <Navbar.Brand className="fw-bold" style={{color: 'black', fontSize: '1.5em' }} href="/">Music Recommandation</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Nav className="me-auto">
                    <Link to="/" style={{fontSize: '1.1em', color: 'black', textDecoration: 'none'}}>Home</Link>
                    <Link to="/emotion" style={{fontSize: '1.1em', color: 'black', textDecoration: 'none'}}>emotion</Link>
                </Nav>
            </Container>
            <Button variant="secondary" onClick={handleShow} className="m-1 mx-lg-4" >
                Login
            </Button>
            <Offcanvas show={show} onHide={handleClose} placement="end">
              <Offcanvas.Header closeButton>
                <Offcanvas.Title>로그인</Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body>
                <Form action={"/member/login"} method = "post">
                    <fieldset>
                        <Form.Group className="mb-3">
                            <Form.Label htmlFor="Id">아이디</Form.Label>
                            <Form.Control id="Id" placeholder="아이디" />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label htmlFor="Pw">패스워드</Form.Label>
                            <Form.Control id="Pw" placeholder="패스워드" />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Check
                                type="checkbox"
                                id="PwSave"
                                label="아이디 저장"
                            />
                        </Form.Group>
                        <Form.Group className="d-flex flex-row gap-3 px-3 mt-lg-3" >
                            <Button variant="secondary" size='lg' type="submit">로그인</Button>
                            <Link to='/register'><Button variant="secondary" size='lg' type="submit" >회원가입</Button></Link>
                            <Button variant="secondary" size='lg' type="submit">회원탈퇴</Button>
                        </Form.Group>
                    </fieldset>
                </Form>
              </Offcanvas.Body>
            </Offcanvas>    
    </Navbar>);
}

export default Header