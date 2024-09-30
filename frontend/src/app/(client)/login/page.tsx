'use client';
import { useEffect, useState } from 'react';
import Image from 'next/image';
import styles from '@/app/ui/client/login/login.module.css';
import { useRouter } from 'next/navigation';
import jwt, { JwtPayload } from 'jsonwebtoken';
import { Permission } from '@/type/permission.dt';
import { useAuth } from '@/app/context/AuthContext';


const LoginComponent = () => {
  const url = "http://localhost:8031/api/v1/auth/anonymous";
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phone, setPhone] = useState('');
  const [loading, setLoading] = useState(false);
  const [loginError, setLoginError] = useState('');
  const [loginSuccess, setLoginSuccess] = useState('');
  const {isAuthenticated, setIsAuthenticated} = useAuth();
  const [showAdditionalFields, setShowAdditionalFields] = useState<Boolean>(false);
  const [fetchUrl,setFetchUrl] = useState('');

  const route = useRouter()

  const handleAddFields = () => {
    setShowAdditionalFields(true);
  };

  const handleResetFields = () => {
    clearField()
    setShowAdditionalFields(false);
  };

  useEffect(() => {
    if(showAdditionalFields){
      setFetchUrl(url+"/register")
    }else{
      setFetchUrl(url+"/login")
    }
    setLoginError('');
    setLoginSuccess('');
  }, [showAdditionalFields]);

  let field = {};
  if(showAdditionalFields){
    field = {email,password,firstName,lastName,phone}
  }else{
    field = {email,password}
  }

  const clearField = () =>{
    setEmail('');
    setPassword('');
    setConfirmPassword('');
    setFirstName('');
    setLastName('');
    setPhone('');
  }
  
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setLoginError('');
    setLoginSuccess('');

    try {
      const response = await fetch(fetchUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(field)
      });

      if (!response.ok) {
        const errorData:CustomError = await response.json();
  
        throw new Error(errorData.error ||'Login failed');
      }
      clearField()
      setShowAdditionalFields(false)
      
      if(showAdditionalFields){
        const data:response<user> = await response.json();
        setEmail(data.result.email);
        setLoginSuccess("register success")
      }else{
        const data:response<token>= await response.json();
        if(data?.result.accessToken){
          const token =data.result.accessToken;
          const decodePermission= jwt.decode(token) as JwtPayload
          const permission = decodePermission?.scope as Permission[]
          localStorage.setItem('token',token)
          localStorage.setItem('permission',JSON.stringify(permission))
          setIsAuthenticated(true);      
          route.push("/")  
        }
      }

    } catch (error) {
      const errorMessage = (error as Error).message;
      setLoginError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className={`h-100 ${styles.gradientForm}`}>
      <div className="container py-5 h-100">
        <div className="row d-flex justify-content-center align-items-center h-100">
          <div className="col-xl-10">
            <div className="card rounded-3 text-black">
              <div className="row g-0">
                <div className="col-lg-6">
                  <div className="card-body p-md-5 mx-md-4">
                    <div className="text-center">
                      <Image
                        src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                        alt="logo"
                        width={185}
                        height={185}
                      />
                      <h4 className="mt-1 mb-5 pb-1">We are The Lotus Team</h4>
                    </div>
                    {loginError && <p className="text-danger">{loginError}</p>}
                    {loginSuccess && <p className="text-success">{loginSuccess}</p>}
                    <form onSubmit={handleSubmit}>
                      <p>Please login to your account</p>
                      <div className="form-outline mb-4">
                        <input
                          type="email"
                          id="form2Example11"
                          className="form-control"
                          placeholder="Phone number or email address"
                          value={email}
                          onChange={(e) => setEmail(e.target.value)}
                        />
                      </div>
                      <div className="form-outline mb-4">
                        <input
                          type="password"
                          id="form2Example22"
                          className="form-control"
                          placeholder="Password"
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                        />
                      </div>
                      {showAdditionalFields && (
                        <>
                          <div className="form-outline mb-4">
                            <input
                              type="password"
                              id="confirmPassword"
                              className="form-control"
                              placeholder="Confirm Password"
                              value={confirmPassword}
                              onChange={(e) => setConfirmPassword(e.target.value)}
                            />
                          </div>
                          <div className="form-outline mb-4">
                            <input
                              type="text"
                              id="phone"
                              className="form-control"
                              placeholder="Phone"
                              value={phone}
                              onChange={(e) => setPhone(e.target.value)}
                            />
                          </div>
                          <div className="form-outline mb-4">
                            <input
                              type="text"
                              id="firstName"
                              className="form-control"
                              placeholder="First Name"
                              value={firstName}
                              onChange={(e) => setFirstName(e.target.value)}
                            />
                          </div>
                          <div className="form-outline mb-4">
                            <input
                              type="text"
                              id="lastName"
                              className="form-control"
                              placeholder="Last Name"
                              value={lastName}
                              onChange={(e) => setLastName(e.target.value)}
                            />
                          </div>
                        </>
                      )}
                      <div className="text-center pt-1 mb-5 pb-1">
                        <button
                          className={`btn btn-primary btn-block fa-lg ${styles.gradientCustom2} mb-3`}
                          style={{ width: '22rem' }}
                          type="submit"
                          disabled={loading}
                        >
                          {loading ? (showAdditionalFields ? 'Registering ' : 'Logging in') : (showAdditionalFields ? 'Register' : 'Log in')}
                        </button>
                        <a className="text-muted" href="#!">Forgot password?</a>
                      </div>
                      <div className="d-flex align-items-center justify-content-center pb-4">
                        <p className="mb-0 me-2">Don't have an account?</p>
                        <button
                          type="button"
                          className="btn btn-outline-danger"
                          onClick={showAdditionalFields ? handleResetFields : handleAddFields}
                        >
                          {showAdditionalFields ? 'Back to Login' : 'Create new'}
                        </button>
                      </div>
                    </form>
                  </div>
                </div>
                <div className={`col-lg-6 d-flex align-items-center ${styles.gradientCustom2}`}>
                  <div className="text-white px-3 py-4 p-md-5 mx-md-4">
                    <h4 className="mb-4">We are more than just a company</h4>
                    <p className="small mb-0">
                      Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default LoginComponent;
