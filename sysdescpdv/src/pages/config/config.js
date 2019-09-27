import React, { Component } from 'react';
import ReactLoading from 'react-loading';
import config from './config.css';
import api from '../../services/api';
import axiosRetry from 'axios-retry';

export default class Config extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            mensagem: 'Carregando Componentes'
        }
    }

    componentDidMount() {

        var me = this;

        axiosRetry(api, {
            retryDelay: axiosRetry.exponentialDelay, retryCondition: function (error) {
                if (error.request.status == 0) {

                    me.setState({ mensagem: "Servidor offline, tentando reconectar" });
                }
                return error.request.status == 0;
            }
        });

        api.get('vendaPDV/adquirirVendaAberta', { data: {} }).then(result => {
            if (result.data) {
                me.setState({ mensagem: "Venda encontrada... carregando" })
            }
        });



        this.setState({ mensagem: "Verificando venda aberta" });
    }

    render() {
        return (
            <div className="content" >
                <div className="loading">
                    <ReactLoading type={'bars'} color={'#FFF'} height={'100%'} width={'100%'} />
                </div>
                <div className="mensagemLoading">{this.state.mensagem}</div>
            </div>
        )
    }


}

