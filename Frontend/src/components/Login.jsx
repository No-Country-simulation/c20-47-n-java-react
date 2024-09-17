import React, { useState  } from "react";
import axios from 'axios';
import './Login.css';
import {useNavigate} from 'react-router-dom';

function Login(){
    const [email, setEmail] = useState('');
    const[password, SetPasword] = useState('');
    const [error, setError] = useState('');
    const [token, setToken] = useState(''); // Nuevo estado para el token
    const [message, setMessage] = useState(''); // Nuevo estado para el mensaje de éxito
    const navigate = useNavigate();
    
    

    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            const response = await axios.post('http://localhost:8084/api/auth/login',{email,password},
                {
                    headers: {
                      'Content-Type': 'application/json',
                    }
            });

           // Manejar la respuesta del backend aquí
           const { Token, Message, Email } = response.data;

           // Almacenar el token en el almacenamiento local
           localStorage.setItem('token', Token);

          // Establecer el token en el estado
          setToken(Token);

          // Establecer el mensaje de éxito
          setMessage(`Bienvenido ${Email}!`);

          

        }catch(err){
            setError("Invalid credentials");

        }
    };

    const goToRegister = ()=>{
      navigate('/register');
    }

    return(

<main class="form-signin w-100 m-auto">
  <form onSubmit={handleSubmit}>
    
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <div class="form-floating">
      <input type="email" value={email} onChange={(e) => setEmail(e.target.value)}  class="form-control" id="floatingInput" placeholder="name@example.com"/>
      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input type="password" value={password} onChange={(e) => SetPasword(e.target.value)} class="form-control" id="floatingPassword" placeholder="Password"/>
      <label for="floatingPassword">Password</label>
    </div>

    <div class="form-check text-start my-3">
      <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault"/>
      <label class="form-check-label" for="flexCheckDefault">
        Remember me
      </label>
      <div className="mt-3 text-center">
        <p>¿No tienes una cuenta?</p>
        <button className="btn btn-secundary" onClick={goToRegister} >Resgistrate aqui</button>
      </div>
      
    </div>
    <button class="btn btn-primary w-100 py-2" type="submit">Sign in</button>
    <p class="mt-5 mb-3 text-body-secondary">&copy; 2017–2024</p>
  </form>
</main>
      
      
           /* {error && <p style={{ color: 'red' }}>{error}</p>}
            {message && <p style={{ color: 'green' }}>{message}</p>}
            {token && <p><strong>Token:</strong> {token}</p>}*/
           
        
        
    );
}


export default Login;

