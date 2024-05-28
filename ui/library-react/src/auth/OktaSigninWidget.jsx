import {useEffect, userRef} from "react";
import OktaSignIn from '@okta/okta-signin-widget';
import {oktaconfig} from "../lib/oktaconfig";

const OktaSigninWidget = ({onSuccess, onError}) => {

    const widgetRef = userRef();

    useEffect(() => {
        if(!widgetRef.current) {
            return false;
        }

        const widget = new OktaSignIn(oktaconfig);

        widget.showSignInToGetTokens( {
            el: widgetRef.current,
            }).then(onSuccess).catch(onError);
        return () => widget.remove();
    }, [onSuccess, onError]);

    return(
      <div className={'container mt-5 mb-5'}>
          <div ref={widgetRef}></div>
      </div>
    );
}

export default OktaSigninWidget;
