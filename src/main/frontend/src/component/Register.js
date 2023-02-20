import { Container,Form, Button } from "react-bootstrap";
import {useEffect, useState} from "react";
// import { useRef } from "react";

export default function Register(){
    // const Nickname = useRef();
    const [nickname, setNickname] = useState('');
    const [password1, setPassword1] = useState('');
    const [password2, setPassword2] = useState('');
    const [email, setEmail] = useState('');

    const onNicknameHandler = (e) =>{
        setNickname(e.target.value);
    }
    const onPassword1Handler = (e) =>{
        setPassword1(e.target.value);
    }
    const onPassword2Handler = (e) =>{
        setPassword2(e.target.value);
    }
    const onEmailHandler = (e) =>{
        setEmail(e.target.value);
    }


    const onSubmitHandler = (e) => {
        e.preventDefault();

        let url = "/"
        console.log(e.target)
        fetch("/member/signup", {
            headers : {"Content-Type":"application/json",
                        Accept : "application/json",},
            method: "POST",
            redirect : "follow",
            body: JSON.stringify({
                nickname: nickname,
                password1: password1,
                password2: password2,
                email:email,
            }),
        })
            .then((response) => response.json()
            )
            .then(data => {
                console.log(data);
                window.alert(data.msg)
                window.location.href =data.url;

            });
    }

    return (
    <Container fluid style={{background:'linear-gradient(#ff74a4 0%, #9f6ea3 100%)', height:"150vh",display:'flex',alignItems: 'center'}}>
        <Container className="bg-secondary" style={{borderRadius: '32px',width:'90%', display:'flex',justifyContent:'center',alignItems: 'center', height: '130vh'}}>
        <Form className="p-3" onSubmit={onSubmitHandler} style={{ width: '80%',height:'90%', backgroundColor: 'white',borderRadius: '32px'}}>
            <Form.Group className="mb-3 mt-4" id="nickname" >
                <Form.Label>아이디</Form.Label>
                <Form.Control type="text" onChange = {onNicknameHandler} name="nickname" placeholder="id" />
            </Form.Group>
            <Form.Group className="mb-3" id="email" >
                <Form.Label>이메일</Form.Label>
                <Form.Control type="email" onChange = {onEmailHandler} placeholder="name@example.com" />
            </Form.Group>
            <Form.Group className="mb-3" id="password1">
                <Form.Label>비밀번호</Form.Label>
                <Form.Control type="password" onChange = {onPassword1Handler} placeholder="password" />
            </Form.Group>
            <Form.Group className="mb-3"  id="password2">
                <Form.Label>비밀번호 확인</Form.Label>
                <Form.Control type="password" onChange = {onPassword2Handler} placeholder="password2" />
            </Form.Group>
            <Form.Group className="mb-3" style={{display:'flex', justifyContent:'flex-end'}}>
                <Button type="submit" size="lg">회원가입 요청</Button>
            </Form.Group>
            </Form>
        </Container>
    </Container>
    );
}