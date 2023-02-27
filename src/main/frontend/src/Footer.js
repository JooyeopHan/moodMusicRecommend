import { Container } from "react-bootstrap"

function Footer(){
    return (        
    <Container fluid bg='light' style={{ height: "40vh",paddingTop:"5vh", }}>
        
        <Container fluid  style={{fontSize: '1rem', color:'black', textAlign:'center'}}>
            (주)엔코아 Playdata 인공지능 24기 Team SoH 파이널 프로젝트 결과물<br />
        </Container>
      </Container>
    );
}

export default Footer