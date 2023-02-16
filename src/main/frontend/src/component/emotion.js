import axios from "axios";
import React from "react";
import {useEffect, useState, useRef} from "react";
import { Container,Button } from "react-bootstrap";


export default function Emotion(){
    const [imgFile, setImgFile] = useState("");
    const [imgFile1, setImgFile1] = useState("");
    const imgRef = useRef();
    const imgRef1 = useRef();
    
    // 이미지 업로드 input의 onChange
    const saveImgFile = () => {
      const file = imgRef.current.files[0];
      const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            setImgFile(reader.result);
        };
    };

    const saveImgFile1 = () => {
      const file = imgRef1.current.files[0];
      const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            setImgFile1(reader.result);
        };
    };

    useEffect(() => {
      console.log('rendering')
    },[])

    const multipartConfig = {
        headers : {
            "Content-Type" : "multipart/form-data"}
    }

    const SubmitHandler  = (e) => {
      e.preventDefault()
      console.log("start")
      const formData = new FormData();
      formData.append("imgBefore" , imgFile);
      formData.append("imgAfter" , imgFile1);
      console.log("form data : " + formData.get("imgBefore"))
      console.log("form data : " + formData.get("imgAfter"))
      axios.post("/recommend/music",formData,multipartConfig,).then(
        (e) => {
          console.log(e.data)
          window.alert(e.data.msg)
        }
      ).catch(function(error){
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
    <Container fluid  style={{backgroundColor:"#44194C", height:"auto", display:'flex', alignItems:'center'}}>
        <Container className="bg-secondary mt-lg-5 mb-lg-5" style={{borderRadius: '32px',width:'90%', display:'flex',flexDirection:'column', height: 'auto'}}>
          <h1 className="mx-auto"> emotion detecting </h1>
          <form onSubmit={SubmitHandler}>
            <Container fluid className="d-flex">
            <Container className=" mx-auto mt-5 d-flex flex-column" style={{width:'45%', height:'45%'}}>  
            <h2 className=" mb-5"> before</h2>
              <img
                className="mb-3"
                src={imgFile ? imgFile :`asset/blank.png`}
                alt="프로필 이미지"
                style={{width: '30vw', height: '40vh', borderRadius: '12px'}}
              />

              <input 
                  type="file"
                  accept="image/*"
                  name='file1'
                  onChange={saveImgFile}
                  ref={imgRef}
              />
            </Container>

            <Container className="mx-auto mt-5 d-flex flex-column" style={{width:'45%', height:'45%'}}>
            <h2 className=" mb-5"> after</h2>
              <img
                className="mb-3"
                src={imgFile1 ? imgFile1 :`asset/blank.png`}
                alt="프로필 이미지"
                style={{width: '30vw', height: '40vh', borderRadius: '12px'}}
              />
                <input 
                  type="file"
                  accept="image/*"
                  name='file2'
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