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
        <Container fluid style={{background:'linear-gradient(#ff74a4 0%, #9f6ea3 100%)', height:"120vh",display:'flex',alignItems: 'center'}}>
            <Container style={{borderRadius: '32px',width:'90%', height: '90vh',display:'flex',justifyContent:'center',alignItems: 'center'}}>
                <Form className="py-auto mx-auto px-4 my-auto" onSubmit={onSubmitHandler} style={{ textAlign:'center',width: '80%',height:'70%', backgroundColor: 'white',borderRadius: '32px', boxShadow: '0 6px 15px rgba(0,0,0,0.15)'}}>
                    <Form.Group className="mb-3 mt-4" id="nickname" >
                        <Form.Label><p className='h4'><strong>아이디</strong></p></Form.Label>
                        <Form.Control type="text" onChange = {onNicknameHandler} name="nickname" placeholder="id" />
                    </Form.Group>
                    <Form.Group className="mb-3" id="email" >
                        <Form.Label><p className='h4'><strong>이메일</strong></p></Form.Label>
                        <Form.Control type="email" onChange = {onEmailHandler} placeholder="name@example.com" />
                    </Form.Group>
                    <Form.Group className="mb-3" id="password1">
                        <Form.Label><p className='h4'><strong>비밀번호</strong></p></Form.Label>
                        <Form.Control type="password" onChange = {onPassword1Handler} placeholder="password" />
                    </Form.Group>
                    <Form.Group className="mb-3"  id="password2">
                        <Form.Label><p className='h4'><strong>비밀번호 확인</strong></p></Form.Label>
                        <Form.Control type="password" onChange = {onPassword2Handler} placeholder="password2" />
                    </Form.Group>
                    <Form.Group className="mb-3" style={{display:'flex', justifyContent:'flex-end'}}>
                        <Button type="submit" variant='secondary' size="lg">회원가입 요청</Button>
                    </Form.Group>
                </Form>
            </Container>
        </Container>
    );
}