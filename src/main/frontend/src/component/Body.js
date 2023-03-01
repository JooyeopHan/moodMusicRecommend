import { Container } from 'react-bootstrap';
import { useEffect,useRef } from 'react';
import { Link } from 'react-router-dom';


function Body(){
    const ref = useRef();
    const ref2 = useRef();
    const ref3 = useRef();
    const ref4 = useRef();
    const ref5 = useRef();
    const Contstyle = {
        height: '90vh', width: '100vw',justifyContent: "center",display:"flex",alignItems: 'center',opacity:0, transition: 'all 0.8s',color:'white'
    }

    useEffect(() => {
        const observer = new IntersectionObserver(
            entries => {
                if (entries[0].isIntersecting) {
                    console.log(entries)
                    entries[0].target.style.opacity=1;

                }
            },
            { threshold: 0.9 }
        );
        //옵져버 탐색 시작
        observer.observe(ref.current);
        observer.observe(ref2.current);
        observer.observe(ref3.current);
        observer.observe(ref4.current);
        observer.observe(ref5.current);
    }, []);


    return(
        <Container fluid style={{background:'linear-gradient(#ff74a4 0%, #9f6ea3 100%)', height:'auto', marginBottom: '3rem'}}>
            <Container ref={ref} style={Contstyle} className='d-flex flex-column'>
                <img src='asset/image4.jpg' className='mb-3' alt="2번 이미지" style={{width: '40vw',borderRadius: '32px', height: '45vh',boxShadow: '0 6px 15px rgba(0,0,0,0.15)'}}/>
            </Container>
            <Container ref={ref2}  style={Contstyle}>
                <h1>오늘 하루 어떠셨나요</h1>
                <img src='asset/image3.jpg' alt="1번 이미지" style={{width: '40vw',borderRadius: '32px', height: '45vh', marginLeft: '1.5em',boxShadow: '0 6px 15px rgba(0,0,0,0.15)'}}/>
            </Container>
            <Container className='d-flex flex-row' ref={ref3} style={Contstyle}>
                <img src='asset/image2.jpeg' alt="2번 이미지" style={{width: '40vw',borderRadius: '32px', height: '45vh', marginRight: '1.5em',boxShadow: '0 6px 15px rgba(0,0,0,0.15)'}}/>
                <h1>여러가지 생각들이 많다면,</h1>
            </Container>
            <Container className='d-flex flex-row' ref={ref4} style={Contstyle}>
                <h1>음악을 들으며 <br/>오늘 하루를<br />위로 받았으면 좋겠어요</h1>
                <img src='asset/image1.png' alt="1번 이미지" style={{width: '40vw',borderRadius: '32px', height: '45vh', marginLeft: '1.5em',boxShadow: '0 6px 15px rgba(0,0,0,0.15)'}}/>
            </Container>
            <Container ref={ref5} style={Contstyle}>
                <h1>당신을 응원합니다</h1>
            </Container>
            <Container style={{textAlign: 'center', height: '20vh'}}>
                <Link to='/emotion' style={{ textDecoration: 'none', color: 'white'}}><h3>감정 기반 음원 추천</h3></Link>
            </Container>
        </Container>
    );
}

export default Body
