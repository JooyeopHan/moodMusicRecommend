import { useState } from "react";
import {Button, Navbar, Offcanvas, Form, Container, Nav, FormGroup} from "react-bootstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import qs from "qs";
import {useCookies} from "react-cookie";
import {getCookie} from "react-use-cookie";

function Header(){

    const [show, setShow] = useState(false);
    const [username, setUsername] = useState('');
    const [passwd, setPasswd] = useState('');
    const [cookies, setCookie] = useCookies([])

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const onUsernameHandler = (e) =>{
        setUsername(e.target.value);
    }
    const onPasswdHandler = (e) =>{
        setPasswd(e.target.value);
    }

    const axiosConfig = {
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded"}
    }

    const axiosBody = {
        username:username,
        passwd:passwd
    }



    const onLoginHandler = (e) =>{
        e.preventDefault();

        axios.post("/member/login",
            qs.stringify(axiosBody),
            axiosConfig)
            .then((response) => {
                console.log(response.data);
                if (response.data.res){
                    setCookie('id', "siteUser");
                    window.location.href =response.data.url;
                }
            })
            .catch((error) => {
                alert('로그인 중 에러가 발생했습니다.');
            });
    }

    const onLogoutHandler = (e) =>{
        e.preventDefault();
        axios.post("/member/logout.do",
            qs.stringify(axiosBody),
            axiosConfig,)
            .then((response) =>{
                setCookie('id', "");
                console.log(response.data);
                window.location.href =response.data.url;
            })
            .catch((error) => {
            window.alert('로그아웃 중 에러가 발생했습니다.');
        });
    }

    const onDeleteHandler = (e) =>{
        e.preventDefault();
        axios.post("/member/delete",
            qs.stringify(axiosBody),
            axiosConfig,)
            .then((response) =>{
                console.log(response.data);
                window.location.href =response.data.url;
            })
            .catch((error) => {
                window.alert('회원탈퇴 중 에러가 발생했습니다.');
            });
    }


        // fetch("/member/login", {
        //     headers : {"Content-Type":"x-www-form-urlencoded",
        //                },
        //     method: "POST",
        //     redirect: "follow",
        //     body: responsebody,
        // }).then(response => response.json())
        //     .then(data=>{
        //         console.log(data);
        //         window.alert(data.msg);
        //         // window.location.href =data.url;
        //     })



  
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
              {(getCookie('id') != "siteUser") ?
                <Form onSubmit={onLoginHandler}>
                    <fieldset>
                        <Form.Group className="mb-3">
                            <Form.Label htmlFor="username">아이디</Form.Label>
                            <Form.Control type ="text" onChange = {onUsernameHandler} id="username" value={username}  placeholder="아이디" />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label htmlFor="passwd">패스워드</Form.Label>
                            <Form.Control type = "password" onChange = {onPasswdHandler} id="passwd" value={passwd} placeholder="패스워드" />
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
                        </Form.Group>
                    </fieldset>
                </Form>
                  :
                <Form className="mb-3">
                    <fieldset>
                        <Form.Group  className= "mb-3">
                            <Button onClick={onLogoutHandler} variant="secondary" size='lg' type="submit">로그아웃</Button>
                        </Form.Group>
                        <Form.Group onClick={onDeleteHandler} className= "mb-3">
                            <Button variant="secondary" size='lg' type="submit">회원탈퇴</Button>
                        </Form.Group>
                    </fieldset>
                </Form>
              }

              </Offcanvas.Body>
            </Offcanvas>    
    </Navbar>);
}

export default Header