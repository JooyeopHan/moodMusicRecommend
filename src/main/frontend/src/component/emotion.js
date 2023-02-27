import axios from "axios";
import React from "react";
import {useEffect, useState, useRef} from "react";
import { Container,Button } from "react-bootstrap";
import "../app.css"


export default function Emotion(){
    const [imgFile, setImgFile] = useState("");
    const [imgFile1, setImgFile1] = useState("");
    const [file1, setFile1] = useState("");
    const [file2, setFile2] = useState("");
    const imgRef = useRef();
    const imgRef1 = useRef();
    const [isLoading, setIsLoading] = useState(false);


    // 이미지 업로드 input의 onChange
    const saveImgFile = () => {

        const file = imgRef.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            console.log("file" + file)
            console.log("reader" + reader)
            console.log("reader_result" + reader.result)
            setImgFile(reader.result);
            setFile1(file);
        };
    };

    const saveImgFile1 = () => {
        const file = imgRef1.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            setImgFile1(reader.result);
            setFile2(file);
        };
    };

    useEffect(() => {
        axios.post("/member/auth",).then(
            (response) => {
                console.log(response.data)
                console.log(response.data.auth)
                let auth = response.data.auth;
                if ((auth !=="ROLE_USER") && (auth !=="ROLE_ADMIN")){
                    window.alert("로그인이 필요한 페이지입니다. 로그인하여 주세요.");
                    window.location.href ="/"
                }
            }
        ).catch(function(error){
            // console.log(error.response.data);
        });
    },[])

    const multipartConfig = {
        headers : {
            "Content-Type" : "multipart/form-data"}
    }

    const SubmitHandler  = (e) => {
        e.preventDefault()
        console.log("start");
        setIsLoading(true);
        const formData = new FormData();

        console.log(imgFile)
        console.log(imgFile1)
        formData.append("file1" , imgFile);
        formData.append("file2" , imgFile1);
        console.log("form data : " + formData.get("file1"))
        // console.log("form data : " + formData.get("imgAfter"))
        axios.post("/recommend/music",formData,multipartConfig,).then(
            (e) => {
                const data = JSON.stringify(e.data.list);
                const result = JSON.stringify(e.data.result);
                console.log(data);
                setIsLoading(false);
                window.localStorage.setItem("music",data);
                window.localStorage.setItem("result",result);
                window.location.href = "/list";
            })
            .catch(function(error){
                if (error.response) {
                    // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                }
                else if (error.request) {
                    // 요청이 이루어 졌으나 응답을 받지 못했습니다.
                    // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
                    // Node.js의 http.ClientRequest 인스턴스입니다.
                    console.log(error.request);
                }
                else {
                    // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
                    console.log('Error', error.message);
                    console.log(error)
                }
                console.log(error.config);
            });
    }



    return (
        <Container fluid  style={{background:'linear-gradient(#ff74a4 0%, #9f6ea3 100%)', height:"auto", display:'flex', alignItems:'center'}}>
            <div style={{ display: isLoading ? 'flex' : 'none' }} className='modal'>
                <div className='modal-content'>
                    <div className='loader'></div>
                    <div className='modal-text'>음악 추천 중입니다... 잠시만 기다려 주세요....</div>
                </div>
            </div>


            <Container className="bg-secondary mt-lg-5 mb-lg-5" style={{borderRadius: '32px',width:'90%', display:'flex',flexDirection:'column', height: 'auto'}}>
                <h1 className="mx-auto mt-lg-3"> 음악 추천을 위해 표정이 담긴 사진을 업로드 해주세요! </h1>
                <form onSubmit={SubmitHandler}>
                    <Container fluid className="d-flex">
                        <Container className=" mx-auto mt-5 d-flex flex-column" style={{width:'45%', height:'45%'}}>
                            <h2 className=" mb-5"> 현재 감정의 표정 </h2>
                            <img
                                className="mb-3"
                                src={imgFile ? imgFile :`asset/blank.png`}
                                alt="프로필 이미지"
                                style={{width: '30vw', height: '40vh', borderRadius: '12px'}}
                            />

                            <input
                                type="file"
                                accept="image/*"
                                // name='file1'
                                // multiple="multiple"
                                onChange={saveImgFile}
                                ref={imgRef}
                            />
                        </Container>

                        <Container className="mx-auto mt-5 d-flex flex-column" style={{width:'45%', height:'45%'}}>
                            <h2 className=" mb-5"> 변하고 싶은 감정의 표정</h2>
                            <img
                                className="mb-3"
                                src={imgFile1 ? imgFile1 :`asset/blank.png`}
                                alt="프로필 이미지"
                                style={{width: '30vw', height: '40vh', borderRadius: '12px'}}
                            />
                            <input
                                type="file"
                                accept="image/*"
                                // name='file2'
                                // multiple="multiple"
                                onChange={saveImgFile1}
                                ref={imgRef1}
                            />
                        </Container>
                    </Container>
                    <Container fluid className="mx-lg-5 mt-lg-5 mb-lg-5" style={{display:'flex',justifyContent: 'flex-start'}}>
                        <Button size="lg" type='submit' >start</Button>
                    </Container>
                </form>

            </Container>
        </Container>
    );
}