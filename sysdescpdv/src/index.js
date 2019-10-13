import React from 'react';
import ReactDOM from 'react-dom';
import RouterPage from './RouterPage';
import index from './index.css';

import * as serviceWorker from './serviceWorker';

ReactDOM.render(<RouterPage />, document.getElementById('root'));


serviceWorker.unregister();
