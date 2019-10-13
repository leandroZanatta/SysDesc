import React from 'react';
import {
    Route,
    HashRouter as Router,
    Switch
} from "react-router-dom";
import logo from './logo.svg';
import Config from './pages/config/config';
import Menu from './pages/menu/menu';
import Venda from './pages/venda/venda';
import Forbiden from './pages/forbiden/forbiden';

function RouterPage() {
    return (
        <Router >
            <Switch >
                <Route path="/" exact component={Config} />
                <Route path="/menuPDV" exact component={Menu} />
                <Route path="/vendaPDV" exact component={Venda} />
                <Route component={Forbiden} />
            </Switch>
        </Router>
    );
}

export default RouterPage;
