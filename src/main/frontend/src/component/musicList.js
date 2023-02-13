import { Container, ListGroup } from "react-bootstrap";

export default function MusicList(){
    const itemcss = {
        color:'black',
        height: '10vh',
        display:'flex',
        alignItems: 'center'
    }



    return( 
        <Container fluid style={{backgroundColor:"#44194C", height:"130vh",display:'flex',alignItems: 'center'}}>
            <Container className="bg-secondary" style={{borderRadius: '32px',width:'100%', display:'flex',alignItems: 'center', height: '110vh'}}>
                <ListGroup variant="flush"  style={{borderRadius: '32px',width:'100%'}}>
                    <ListGroup.Item style={itemcss} >Cras justo odio</ListGroup.Item>
                    <ListGroup.Item style={itemcss}>Dapibus ac facilisis in</ListGroup.Item>
                    <ListGroup.Item style={itemcss}>Morbi leo risus</ListGroup.Item>
                    <ListGroup.Item style={itemcss}>Porta ac consectetur ac</ListGroup.Item>
                    <ListGroup.Item style={itemcss} >Cras justo odio</ListGroup.Item>
                    <ListGroup.Item style={itemcss}>Dapibus ac facilisis in</ListGroup.Item>
                    <ListGroup.Item style={itemcss}>Morbi leo risus</ListGroup.Item>
                    <ListGroup.Item style={itemcss}>Porta ac consectetur ac</ListGroup.Item>
                    <ListGroup.Item style={itemcss} >Cras justo odio</ListGroup.Item>
                    <ListGroup.Item style={itemcss}>Dapibus ac facilisis in</ListGroup.Item>
                </ListGroup>
            </Container>
        </Container>
  );
}