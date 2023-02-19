import {Accordion, Button, Collapse, Container, Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import {Helmet} from "react-helmet";
import "./list.css";



export default function MusicList1(){
    const itemcss = {
        color:'white',
        height: '10vh',
        display:'flex',
        alignItems: 'center'
    }
    const allMusic = JSON.parse(window.localStorage.getItem("music"));
    console.log(allMusic)


    const [play, setplay] = useState(false);
    const [current,setCurrent] = useState(0);
    const [music, setMusic] = useState(allMusic[0]);
    const [proup, setProup] = useState(0);
    const changeVolume = (v) => {
        console.log(v)
        const new_v = (music.volume + v)/100;
        if( 0 <= new_v && new_v <= 1.0){
            music.volume = new_v;
        }
    }

    const playing = () => {
        setplay(true);
        console.log("play"+current);

        music.play();

        setProup(setInterval(() => {
            console.log("current time ", music.currentTime);
            // console.log("max duration: ", music.duration);
            // 퍼센트 -> 현재시간 / 전체시간 * 100
            let current_progress = music.currentTime / music.duration * 100;
            // 진행바에 퍼센트 업데이트 쳐준다
            document.getElementById('myBar').style.width = current_progress + '%';

            // console.log("current progress ", current_progress);
        }, 100));
    }

    const stopping = () => {
        setplay(false);
        music.currentTime = 0;
        music.pause();
        clearInterval(proup);
    }

    const pauseing = () => {
        setplay(false);
        music.pause();
        clearInterval(proup);
    }

    useEffect(() => {
        const currentAudio = new Audio(allMusic[current].audio);
        setMusic(currentAudio);
        console.log("use" + current);
    }, [current])

    const clickListener = (e) => {
        setplay(false);
        music.currentTime = 0;
        music.pause();
        clearInterval(proup);
        setCurrent(e.target.value);
    }

    const [volume, setVolume] = useState(50)


    return(
        <Container fluid style={{background: 'linear-gradient(#ff74a4 0%, #9f6ea3 100%)', height:"210vh", width: '100vw',display:'flex',flexDirection:'column',alignItems: 'center'}}>

            <Helmet>
                <meta charSet="utf-8" />
                <title>mp3Player</title>
                <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet" />
                <link href="https://fonts.googleapis.com/css2?family=Material+Icons" rel="stylesheet" />
                <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
            </Helmet>

            <Container fluid className="bg-white mt-lg-5" style={{display:'flex',flexDirection:'row',height: "28%", width:'70%', borderRadius: '32px', boxShadow: '0 6px 15px rgba(0,0,0,0.15)'}}>
                <img src={allMusic[current].img ? allMusic[current].img : "asset/blank.png"} className="m-4 my-auto" style={{
                    height: "40vh",
                    width: "40vw",
                    margin: "auto",
                    borderRadius: '32px',
                    boxShadow: '0 6px 15px rgba(0,0,0,0.15)'
                }} />
                <Container fluid className="mt-lg-3" style={{display:'flex 5'}}>
                    <div className="" style={{
                        position: "relative",
                        height: "50%"
                    }}>
                        <div className="mt-lg-4" style={{
                            fontSize: "3em"
                        }}>{allMusic[current].track}</div>
                        <p className="mt-3" style={{
                            fontSize: "1.5em"
                        }}>{allMusic[current].artist}</p>

                        <Container className="mt-lg-5">
                            <div id="myProgress">
                                <div id="myBar"></div>
                            </div>
                        </Container>

                        <Container fluid className="controller mt-lg-5" style={{
                            display: "flex",
                            justifyContent: "center",
                            flexDirection: "row",
                            float: "left"
                        }}>
                            {play ?
                            <i className="material-icons" onClick={pauseing}>pause</i>:
                                <i className="material-icons" onClick={playing}>play_arrow</i>}
                            <i className="material-icons" onClick={stopping}>stop</i>

                            <Container className="mt-4" style={{
                                justifyContent: "center",
                                flexDirection: "row",
                            }}>
                            <input className="" type="range" min={0} max={100} step={1} value={volume} onChange={event => {setVolume(event.target.valueAsNumber);changeVolume(event.target.valueAsNumber)}} />
                            </Container>

                        </Container>
                    </div>
                </Container>

            </Container>


            <Table hover className="mt-lg-5" style={{width:'80%', height:"60%"}}>
                <thead>
                <tr>
                    <th>#</th>
                    <th>artist</th>
                    <th>track name</th>
                    <th>link</th>
                </tr>
                </thead>
                <tbody>
                {allMusic.map((m,index)=> {
                    return(
                        <tr key={index}>
                            <td>{index+1}</td>
                            <td>{m.artist}</td>
                            <td>{m.track}</td>
                            <td><Button value={`${index}`} onClick={clickListener}>#</Button></td>
                        </tr>);
                })}
                </tbody>
            </Table>
        </Container>
    );
}

