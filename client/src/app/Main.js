/**
 * In this file, we create a React component
 * which incorporates components provided by Material-UI.
 */
import React, {Component} from 'react';
import get from 'axios';
import AutoComplete from 'material-ui/AutoComplete';
import {deepOrange500} from 'material-ui/styles/colors';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

const styles = {
  container: {
    textAlign: 'center',
    paddingTop: 200,
  },
};

const muiTheme = getMuiTheme({
  palette: {
    accent1Color: deepOrange500,
  },
});

class Main extends Component {
  constructor(props, context) {
    super(props, context);

    this.state = {
      dataSource: [],
    };
    this.handleUpdate = this.handleUpdate.bind(this)
  }

  handleUpdate(input) {
    var self = this;
    get(`/alpha/${input}`)
     .then(function(value){
        self.setState({
            dataSource:value.data
           });
       })
    }


  render() {

    return (
      <MuiThemeProvider muiTheme={muiTheme}>
        <div style={styles.container}>
          <AutoComplete
	  hintText="Type anything"
	  dataSource={this.state.dataSource}
	  onUpdateInput={this.handleUpdate}
          />
        </div>
      </MuiThemeProvider>
    );
  }
}

export default Main;
