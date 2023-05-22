package com.arturbogea.consultacep.api

import com.arturbogea.consultacep.model.Endereco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    // precisamos criar uma rota get. Para que possamos buscar todos os dados dessa rota. Para que possa ser consumido, todos dados que vem da api viacep
    // ele retorna valores do tipo string
    // abaixo, passamos apenas o final do link viacep.com.br/ws/01001000/json/
    // no link aparece esse cep 01001000, que é um cep generico. Mas precisará ser o cep que o usuario digitará no campo de cep.
    // por isso vamos criar uma variavel que iremos passar para a rota
    @GET("ws/{cep}/json/")
    // para isso, vamos criar um metodo publico, e nos parametros, irei passar um caminho. Colocamos o Path, que é o caminho para o retrofit. Logo depois. criamos um objeto(propridade)
    // esse metodo, irá herdar de uma chamada (callback). Utilizaremos um Call, iremos fazer uma chamada para o Api. Precisamos passar um objeto dentro do <>, com as informações.
    // Para isso, precisarei criar uma classe chamada endereço
    fun setEndereco(@Path("cep") cep: String) : Call<Endereco>
}