import React from 'react';
import {
    Link,
    Route,
    HashRouter as Router,
    Switch
} from "react-router-dom";
import logo from './logo.svg';
import Config from './pages/config/config';
import Forbiden from './pages/forbiden/forbiden';

function RouterPage() {
    return ( 
	<Router >
        <Switch >
			<Route path = "/"  exact component = { Config } /> 
			<Route component = { Forbiden }/>
		</Switch> 
	</Router>
    );
}

export default RouterPage;
