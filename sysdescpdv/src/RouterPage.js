import React from 'react';
import logo from './logo.svg';
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import Config from './pages/config/config';
import Forbiden from './pages/forbiden/forbiden';


function RouterPage() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact component={Config} />
        <Route component={Forbiden} />
      </Switch>
    </Router>
  );
}

export default RouterPage;