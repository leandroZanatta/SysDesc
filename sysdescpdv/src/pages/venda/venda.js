import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col'
import { AgGridReact } from 'ag-grid-react';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-balham.css';
import venda from './venda.css';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
export default class Venda extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            columnDefs: [{
                headerName: "SEQ", field: "sequencial", width: 60
            }, {
                headerName: "DESCRIÇÃO", field: "descricao", width: 448
            }, {
                headerName: "PREÇO UNITÁRIO", field: "precoUnitario", width: 120
            }, {
                headerName: "QUANTIDADE", field: "quantidade", width: 120
            }, {
                headerName: "VALOR TOTAL", field: "valorTotal", width: 120
            }],
            rowData: [{
                sequencial: 1, descricao: "PRODUTO 01", precoUnitario: 3.56, quantidade: 1, valorTotal: 3.5
            }],
            quantidade: 1
        }
    }


    keyDownBarras = (event) => {

        if (event.key == '*') {
            this.setState({ quantidade: parseInt(event.target.value) });

            this.setState({ codigoBarras: '' });
            event.target.value = '';
            event.preventDefault();
        }

        if (!((event.keyCode > 96 && event.keyCode < 105) || (event.keyCode > 48 && event.keyCode < 57))) {
            event.preventDefault();
        }
    }

    render() {
        return (
            <Container>
                <Row>
                    <Col>
                        <InputGroup className="mb-3" style={{ marginTop: 50 }}>
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroup-sizing-lg" style={{ width: 80 }}>{this.state.quantidade}</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                placeholder="Código de Barras"
                                aria-label="Username"
                                aria-describedby="basic-addon1"
                                onKeyDown={this.keyDownBarras}
                            />
                        </InputGroup>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <div
                            className="ag-theme-balham"
                            style={{
                                marginTop: 20,
                                height: '300px',
                                flex: 1
                            }}
                        >
                            <AgGridReact
                                columnDefs={this.state.columnDefs}
                                rowData={this.state.rowData}
                            >

                            </AgGridReact>
                        </div>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        CAMPO 1
                    </Col>
                    <Col>
                        CAMPO 2
                    </Col>
                    <Col>
                        CAMPO 3
                    </Col>
                </Row>
            </Container>
        )
    }
}