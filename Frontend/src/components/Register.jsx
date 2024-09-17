
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";
import React,{useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function Register(){
    const [formData, setFormData] = useState({
        name:'',
        surname:'',
        email:'',
        password:'',
        roles:'',
        accountType:'',
    });

    const handleRolesChange = (event) => {
        const options = event.target.options;
        const selectedRoles = [];
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                selectedRoles.push(options[i].value);
            }
        }
        setFormData({
            ...formData,
            rol: selectedRoles
        });
    };
    
    const handleChange = (e) =>{
        setFormData({
            ...formData,
            [e.target.name]:e.target.value,
        });
    };
    const handleSubmit = async (e) =>{
        e.preventDefault();

        fetch("http://localhost:8084/api/user/createUser",{
            method:"POST",
            headers: {
                "Content-Type": "application/json",
            },
            body:JSON.stringify({...formData,
                roles:formData.roles
            }),
        })
        .then(response=>response.json())
        .then(data =>{
            console.log("Registro exitoso",data);
        })
        .catch (error =>{
            console.log("Error al registrar:",error);
        });
    
    };

    return(

        <div class="container mt-5 d-flex flex-column align-items-center">
    
        <h2 class="mb-4">Formulario de Registro</h2>
    
    
        <form onSubmit={handleSubmit} class="row g-3 needs-validation w-75" novalidate>
            <div class="col-md-4">
                <label for="name" class="form-label">Nombre</label>
                <input type="text" name="name" value={formData.name} onChange={handleChange} class="form-control" id="name" required />
                <div class="valid-feedback">
                    ¡Se ve bien!
                </div>
            </div>
            <div class="col-md-4">
                <label for="apellido" class="form-label">Apellido</label>
                <input type="text" name="surname" value={formData.surname} onChange={handleChange} class="form-control" id="surname" required />
                <div class="valid-feedback">
                    ¡Se ve bien!
                </div>
            </div>
            <div class="col-md-4">
                <label for="email" class="form-label">Email</label>
                <input type="text" name="email" value={formData.email} onChange={handleChange} class="form-control" id="email" required />
                <div class="valid-feedback">
                    ¡Se ve bien!
                </div>
            </div>
            <div class="col-md-4">
                <label for="password" class="form-label">Contraseña</label>
                <input type="password" name="password" value={formData.password} onChange={handleChange} class="form-control" id="password" required />
                <div class="invalid-feedback">
                    Proporcione una contraseña válida.
                </div>
            </div>
            <div class="col-md-4">
                <label for="roles" class="form-label">Rol</label>
                <select name="roles"   onChange={handleRolesChange} class="form-select" id="roles" required>
                    <option selected disabled value="">Opciones</option>
                    <option value="CLIENTE">Cliente</option>
                    <option value="EMPRESA">Empresa</option>
                    <option value="ADMINISTRADOR">Administrador</option>
                </select>
                <div class="invalid-feedback">
                    Seleccione un rol válido.
                </div>
            </div>
            <div class="col-md-4">
                <label for="tipoCuenta" class="form-label">Tipo de cuenta bancaria</label>
                <select name="accountType" value={formData.accountType} onChange={handleChange} class="form-select" id="accountType" required>
                    <option selected disabled value="">Opciones</option>
                    <option value="CUENTA_CORRIENTE">Caja de cuenta corriente</option>
                    <option value="CUENTA_AHORRO">Caja de ahorro en pesos</option>
                    <option value="CUENTA_SUELDO">Cuenta sueldo</option>
                    <option value="CUENTA_DOLAR">Cuenta dolar</option>
                </select>
                <div class="invalid-feedback">
                    Seleccione un tipo de cuenta válido.
                </div>
            </div>
            <div class="col-12">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="invalidCheck" required />
                    <label class="form-check-label" for="invalidCheck">
                        Acepto los términos y condiciones
                    </label>
                    <div class="invalid-feedback">
                        Debes aceptar antes de enviar.
                    </div>
                </div>
            </div>
            <div class="col-12 text-center">
                <button class="btn btn-primary" type="submit">Enviar formulario</button>
            </div>
        </form>
    </div>

    );
}


export default Register;