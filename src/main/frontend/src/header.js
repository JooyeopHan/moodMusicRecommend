import {useEffect, useState} from "react";
import {Button, Navbar, Offcanvas, Form, Container, Nav, FormGroup} from "react-bootstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import qs from "qs";
import './header.css';
import {useCookies} from "react-cookie";
import {getCookie} from "react-use-cookie";

function Header(){

    const [show, setShow] = useState(false);
    const [username, setUsername] = useState('');
    const [passwd, setPasswd] = useState('');
    const [auth, setAuth] = useState("");
    const [logIned, setLogIned] = useState(false);
    const [nickname, setNickname] = useState("");
    // const [email, setEmail] = useState("");

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

    useEffect(() => {
        console.log('렌더링 완료')
        axios.post("/member/auth",).then(
            (response) => {
                if ((response.data.auth ==="ROLE_USER") || (response.data.auth ==="ROLE_ADMIN")) {
                    setLogIned(true)
                }
            }
        ).catch(function(error){
            console.log(error);
        });
    },[])


    useEffect(() => {
        axios.post("/member/profile",).then(
            (response) => {
                console.log(response.data)
                console.log(response.data.nickname)
                setNickname(response.data.nickname)
                // setEmail(response.data.email)
                console.log(nickname)
            }
        ).catch(function(error){
            // console.log(error.response.data);
        });
    },[])

    const onLoginHandler = (e) =>{
        e.preventDefault();

        axios.post("/member/login",
            qs.stringify(axiosBody),
            axiosConfig)
            .then((response) => {
                console.log(response.data);
                if (response.data.res){
                    setAuth(response.data.auth);
                    console.log("auth : " + auth)
                    window.location.href =response.data.url;
                    window.alert(response.data.msg);
                }
                else{
                    window.location.href =response.data.url;
                    window.alert(response.data.msg);
                }
            })
            .catch((error) => {
            });
    }

    const onLogoutHandler = (e) =>{
        e.preventDefault();
        axios.post("/member/logout.do",
            qs.stringify(axiosBody),
            axiosConfig,)
            .then((response) =>{
                window.location.href =response.data.url;
                window.alert(response.data.msg);
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
                window.location.href =response.data.url;
            })
            .catch((error) => {
                window.alert('회원탈퇴 중 에러가 발생했습니다.');
            });
    }




    return (
        <Navbar bg="light" expand="lg" style={{height :'15vh', textAlign: 'center', display: 'flex', alignItems:'center',boxShadow: '1px 1px 3px 1px #44194C'}}>
            <Container fluid style={{justifyContent:'around' }}>
                <Navbar.Brand className="fw-bold" style={{color: 'black', fontSize: '1.5em' }} href="/">Music Recommandation</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Nav className="me-auto">
                    <Link to="/" style={{fontSize: '1.1em', color: 'black', textDecoration: 'none'}}>Home</Link>
                    <Link to="/emotion" style={{fontSize: '1.1em',marginLeft:"1rem", color: 'black', textDecoration: 'none'}}>emotion</Link>
                </Nav>
            </Container>
            <Button variant="secondary" onClick={handleShow} className="m-1 mx-lg-4" >
                Login
            </Button>
            <Offcanvas show={show} onHide={handleClose} placement="end">
                <Offcanvas.Header closeButton>
                    <Offcanvas.Title>{!logIned ? '로그인' : '반갑습니다 '+nickname+'님' }</Offcanvas.Title>
                </Offcanvas.Header>
                <Offcanvas.Body>
                    {!logIned ?
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
                            <fieldset className="field mt-3" >
                                <a className="mt-3" onClick={onLogoutHandler} type="submit">로그아웃</a>
                                <a className="mt-3" onClick={onDeleteHandler} type="submit">회원탈퇴</a>
                                <a className="mt-3" href="/profile" type="submit">회원정보</a>
                            </fieldset>
                        </Form>
                    }

                </Offcanvas.Body>
            </Offcanvas>
        </Navbar>);
}

export default Header