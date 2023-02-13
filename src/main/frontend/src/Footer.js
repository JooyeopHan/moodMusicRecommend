import { Container } from "react-bootstrap"

function Footer(){
    return (        
    <Container fluid bg='light' style={{ height: "40vh",paddingTop:"5vh", }}>
        
        <Container fluid  style={{fontSize: '1rem', color:'black', textAlign:'center'}}>
            1조 프로젝트 진행 중입니다.<br />
            화이팅 합시다.
        </Container>
      </Container>
    );
}

export default Footer