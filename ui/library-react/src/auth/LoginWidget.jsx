import {useOktaAuth} from "@okta/okta-react";
import {SpinnerLoading} from "../layouts/utils/SpinnerLoading";
import OktaSigninWidget from "./OktaSigninWidget";
import {toRelativeUrl} from "@okta/okta-auth-js";
import {Outlet} from "react-router-dom";


const LoginWidget = ({config}) => {
    const {oktaAuth, authState} = useOktaAuth();
    const onSuccess = (tokens) => {
        oktaAuth.handleLoginRedirect(tokens);
    };

    const onError = (error) => {
        console.log('Sign in error', error)
    }

    if(!authState) {
        return (
            <SpinnerLoading/>
        )
    }

    if (!authState?.isAuthenticated) {
        const originalUri = toRelativeUrl(window.location.href, window.location.origin);
        oktaAuth.setOriginalUri(originalUri);
        oktaAuth.signInWithRedirect();
    } else {
        <OktaSigninWidget config={config} onSuccess={onSuccess} onError={onError}/>
    }
    return (<Outlet/>);
}

export default LoginWidget;
