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

        this.currency = new Intl.NumberFormat('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });

        this.state = {
            columnDefs: [{
                headerName: "SEQ", field: "sequencial", minWidth: 60, maxWidth: 60
            }, {
                headerName: "DESCRIÇÃO", field: "descricao"
            }, {
                headerName: "PREÇO UNITÁRIO", field: "precoUnitario", minWidth: 120, maxWidth: 120, valueFormatter: this.currencyFormatter
            }, {
                headerName: "QUANTIDADE", field: "quantidade", minWidth: 120, maxWidth: 120, valueFormatter: this.quantidadeFormatter
            }, {
                headerName: "VALOR TOTAL", field: "valorTotal", minWidth: 120, maxWidth: 120, valueFormatter: this.currencyFormatter
            }],
            rowData: [{
                sequencial: 1, descricao: "PRODUTO 01", precoUnitario: 34053.56, quantidade: { valor: 1, casasDecimais: 0 }, valorTotal: 3.5
            }],
            quantidade: 1
        }
    }

    quantidadeFormatter = (campo) => {
        return new Intl.NumberFormat('pt-BR', { minimumFractionDigits: campo.value.casasDecimais, maximumFractionDigits: campo.value.casasDecimais }).format(campo.value.valor);
    }

    currencyFormatter = (campo) => {
        return this.currency.format(campo.value);
    }

    keyDownBarras = (event) => {

        if (event.key == '*') {
            this.setState({ quantidade: parseInt(event.target.value) });

            this.setState({ codigoBarras: '' });
            event.target.value = '';
            event.preventDefault();
        }

        if (!((event.keyCode > 95 && event.keyCode < 106) || (event.keyCode > 47 && event.keyCode < 58))) {
            event.preventDefault();
        }
    }

    render() {
        return (

            <div class="container">
                <div class="box">

                    <InputGroup className="mb-3" style={{ paddingTop: 50 }}>
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

                </div>
                <div class="box grid-venda ag-theme-balham"
                    style={{
                        marginTop: 20
                    }}>

                    <AgGridReact
                        columnDefs={this.state.columnDefs}
                        rowData={this.state.rowData}
                        enableRangeSelection={true}
                        animateRows={true}
                        onGridReady={(params) => {

                            params.api.sizeColumnsToFit();
                        }}
                    >

                    </AgGridReact>
                </div>
                <div class="box footer-venda">box 3</div>
            </div>
        )
    }
}