import { Container,Form, Button } from "react-bootstrap";
import {useEffect, useState} from "react";
// import { useRef } from "react";

export default function Register(){
    // const Nickname = useRef();

    const onSubmitHandler = (e) => {
        e.preventDefault();
        console.log(e.target)
        fetch("/member/signup", {
            headers : {"Content-Type":"application/json"},
            method: "POST",
            body: JSON.stringify({
                nickname: "닉네임1",
                password1: "1234",
                password2: "1234",
                email:"12341@naver.com"
            }),
        })
            .then((response) => console.log(response.json()))
            .then((result) => console.log(result));
    }

    return (
    <Container fluid style={{backgroundColor:"#44194C", height:"150vh",display:'flex',alignItems: 'center'}}>
        <Container className="bg-secondary" style={{borderRadius: '32px',width:'90%', display:'flex',justifyContent:'center',alignItems: 'center', height: '130vh'}}>
        <Form className="p-3" onSubmit={onSubmitHandler} style={{ width: '80%',height:'90%', backgroundColor: 'white',borderRadius: '32px'}}>
            <Form.Group className="mb-3 mt-4" id="username" >
                <Form.Label>아이디</Form.Label>
                <Form.Control type="id" name="Id" placeholder="username" />
            </Form.Group>
            <Form.Group className="mb-3" id="email" >
                <Form.Label>이메일</Form.Label>
                <Form.Control type="email" placeholder="name@example.com" />
            </Form.Group>
            <Form.Group className="mb-3" id="password">
                <Form.Label>비밀번호</Form.Label>
                <Form.Control type="password" placeholder="password" />
            </Form.Group>
            <Form.Group className="mb-3"  id="password2">
                <Form.Label>비밀번호 확인</Form.Label>
                <Form.Control type="password" placeholder="password2" />
            </Form.Group>
            <Form.Group className="mb-3" style={{display:'flex', justifyContent:'flex-end'}}>
                <Button type="submit" size="lg">회원가입 요청</Button>
            </Form.Group>
            </Form>
        </Container>
    </Container>
    );
}