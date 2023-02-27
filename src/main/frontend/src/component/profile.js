import React, {useEffect, useState} from 'react';
import { MDBCol, MDBContainer, MDBRow, MDBCard, MDBCardText, MDBCardBody, MDBCardImage, MDBBtn, MDBTypography } from 'mdb-react-ui-kit';
import axios from "axios";
import {Button, Container, Table} from "react-bootstrap";

export default function Profile() {

    const [profile, setProfile] = useState({"nickname" : "", "email" : ""});
    const [set,setSet] = useState([]);
    const [mlist,setMlist] = useState([]);
    const [curEmo,setCurEmo] = useState("");
    const [curIdx, setCurIdx] = useState({});
    const [image,setImage] = useState("");
    const [audio, setAudio] = useState("");


    useEffect(() => {
        axios.post("/member/profile",)
            .then((response) => {
                    console.log(response)
                    console.log(response.data.nickname)
                    setProfile((prevState) =>{
                        return {...prevState, nickname : response.data.nickname, email : response.data.email}
                    });
                }
            ).catch(function(error){
            // console.log(error.response.data);
        });
        axios.post("/recommend/profile",)
            .then((response) => {
                    console.log("recommend response : " +response)
                    console.log("recommend response11 : " +response.data)
                    const listing = [];
                    const setting = new Set();
                    for(let i = 0 ; i<response.data.length; i++){
                        let a = JSON.parse(JSON.stringify(response.data[i]))
                        console.log(a)
                        listing.push(a)
                        setting.add(a['emotion']);
                    }
                    setSet([...setting]);
                    setMlist([...listing]);

                }
            ).catch(function(error){
            // console.log(error.response.data);
        });
    },[])


    const buttonListener = (e) =>{
        console.log(e.target.value );
        setCurEmo(e.target.value);
    }

    const playHandler = (e) => {
        let id = e.target.value
        id *= 1;
        mlist.map((a) => {
            if(a.id === id){
                console.log(a);
                setCurIdx(a);
            }
        })
    }

    // onClick={() => setOpen(!open)}
    // aria-controls="example-collapse-text"
    // aria-expanded={open}

    return (
        <div className="gradient-custom-2" style={{ background: 'linear-gradient(#ff74a4 0%, #9f6ea3 100%)' }}>
            <MDBContainer className="py-5 h-auto">
                <MDBRow className="justify-content-center align-items-center h-100">
                    <MDBCol lg="11" xl="7">
                        <MDBCard>
                            <div className="rounded-top text-white d-flex flex-row" style={{ backgroundColor: '#000', height: '200px' }}>
                                <div className="ms-4 mt-5 d-flex flex-column" style={{ width: '150px' }}>
                                    <MDBCardImage src="asset/blank.png"
                                                  alt="Generic placeholder image" className="mt-4 mb-2 img-thumbnail" fluid style={{ width: '150px', zIndex: '1' }} />
                                </div>
                                <div className="ms-3" style={{ marginTop: '130px' }}>
                                    <MDBTypography tag="h5"> {profile.nickname} </MDBTypography>
                                    <MDBCardText> {profile.email}</MDBCardText>
                                </div>
                            </div>
                            <div style={{ height:'5vh', backgroundColor: '#f8f9fa' }}>

                            </div>
                            <MDBCardBody className="text-black p-4">
                                <div className="mb-5">
                                    <p className="lead fw-normal mb-1">emotions list</p>
                                    <div className="p-4 g-5" style={{ backgroundColor: '#f8f9fa' }}>
                                        {set.map((a)=>{
                                            return <Button variant='link' style={{ color:"black", textDecoration:'none'}} value={`${a}`} onClick={buttonListener}>{a}</Button>;
                                        })}
                                    </div>
                                </div>
                                <div className="mb-5" style={{ backgroundColor: '#f8f9fa' }}>
                                    <Table hover>
                                        <thead>
                                        <tr>
                                            <th>artist</th>
                                            <th>track</th>
                                            <th>play</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {mlist.map((a)=>{
                                            if(a['emotion'] === curEmo) {
                                                return (<tr>
                                                    <td>{a['artist']}</td>
                                                    <td>{a['musicName']}</td>
                                                    <td><Button onClick={playHandler} variant='link' style={{ color:"black", textDecoration:'none'}} value={a.id}>play</Button></td>
                                                </tr>);
                                            }
                                        })}
                                        </tbody>
                                    </Table>
                                </div>
                                <Container className='d-flex flex-row p-4' style={{ backgroundColor: '#f8f9fa',borderRadius: '32px' }}>
                                    <img src={ curIdx.img ? curIdx.img : "asset/blank.png"} style={{
                                        height: "25vh",
                                        width: "25vw",
                                        borderRadius: '32px',
                                        boxShadow: '0 6px 15px rgba(0,0,0,0.15)'
                                    }} />
                                    <Container className='g-5 justify-content-center p-3' style={{position: 'relative'}}>
                                        <h4>{ curIdx.musicName }</h4>
                                        <h5>{ curIdx.artist }</h5>
                                        <Container style={{position:"absolute", bottom: 1}}>
                                            {curIdx.audio !== null ? <audio controls='controls' src={curIdx.audio}/> : <h5>죄송합니다. 곡이 존재하지 않습니다.</h5> }
                                        </Container>
                                    </Container>
                                </Container>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </div>
    );
}