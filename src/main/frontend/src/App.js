import 'bootstrap/dist/css/bootstrap.min.css';
import Body from "./component/Body";
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Header from './header';
import Footer from './Footer';
import MusicList1 from './component/musicList1';
import Emotion from './component/emotion';
import Register from './component/Register';
import {CookiesProvider} from "react-cookie";


function App() {
  return (
      <div className="App" style={{height:'auto'}}>
        <CookiesProvider>
            <BrowserRouter>
                <Header/>
                <Routes>
                    <Route path='/' element={<Body />}/>
                    <Route path='/list' element={<MusicList1 />} />
                    <Route path='/emotion' element={<Emotion />} />
                    <Route path='/register' element={<Register />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </CookiesProvider>

      </div>
  );
}

export default App;

// export default App;
// import logo from './logo.svg';
// import './App.css';
//
// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }
//
// export default App;
