import React from 'react';
import Main from './page/main/Main';
import Home from './page/home/Home';
import { Route } from 'react-router';
import { BrowserRouter, Switch } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <Route path='/' exact component={Main} />
    </BrowserRouter>
  );
}

export default App;
