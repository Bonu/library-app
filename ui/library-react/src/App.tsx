import React from 'react';

import './App.css';
import {Navbar} from "./layouts/navbarandfooter/Navbar";
import {Footer} from "./layouts/navbarandfooter/Footer";
import {HomePage} from "./layouts/homepage/HomePage";
import {SearchBookPage} from "./searchbookpage/SearchBookPage";
import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {BookCheckoutPage} from "./layouts/bookcheckoutpage/BookCheckoutPage";
import {OktaAuth, toRelativeUrl} from '@okta/okta-auth-js'
import {oktaconfig} from "./lib/oktaconfig";
import {Security, LoginCallback} from "@okta/okta-react";
import LoginWidget from "./auth/LoginWidget";

const oktaAuth = new OktaAuth(oktaconfig)

export const App = () => {

    const navigate = useNavigate();
    const restoreOriginalUri = (_oktaAuth: any,  originalUri: string) => {
        navigate(toRelativeUrl(originalUri || '/', window.location.origin));
    };

  return (
      <div className={'d-flex flex-column min-vh-100'}>
          <Security oktaAuth={oktaAuth} restoreOriginalUri={restoreOriginalUri}>

          <Navbar/>
          <div className={'flex-grow-1'}>
              <Routes>
                  <Route path='/' element={<HomePage/>}/>
                  <Route path='/home' element={<HomePage/>}/>
                  <Route path='' element={<HomePage/>}/>
                  <Route path='/search' element={<SearchBookPage/>}/>
                  <Route path='/checkout/:bookid' element={<BookCheckoutPage/>}/>
                  <Route path='/login' element={<LoginWidget config={oktaconfig} />}/>
                  <Route path='/login/callback' element={<LoginCallback/>}/>
              </Routes>
          </div>
          <Footer/>
        </Security>
      </div>
)
    ;
}
