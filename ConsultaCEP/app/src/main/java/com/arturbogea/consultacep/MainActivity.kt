package com.arturbogea.consultacep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arturbogea.consultacep.api.API
import com.arturbogea.consultacep.databinding.ActivityMainBinding
import com.arturbogea.consultacep.model.Endereco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Abaixo, vamos configurar o Retrofit
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) //aqui é converter o Json
            .baseUrl("https://viacep.com.br/ws/")
            .build() // aqui é para executar
            .create(API::class.java) // aqui estamos passando a nossa interface API, que criei.

        binding.btBuscarcep.setOnClickListener {

            val cep = binding.editCep.text.toString()

            if (cep.isEmpty()){
                Toast.makeText(this, "Digite um CEP valido", Toast.LENGTH_LONG).show()
            }else{
                /*
                aqui a veriavel retrofit que foi lincada com o api, está tendo acesso ao metodo setEndereco. Quando colocamos a informação que precisamos, o Path irá receber e passará para o GET, onde está vinculado o link
                e buscará na api, o endereço referente ao cep
                 */
                // o metodo enqueue, espera um callback do retrofit.
                retrofit.setEndereco(cep).enqueue(object : Callback<Endereco>{
                    // aqui no metodo onResponse, vai ser a resposta da Api
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        if (response.code() == 200){ // se o responde.code tiver uma resposta de 200, foi um sucesso. Iremos conseguir recuperar os dados. Irei criar uma variavel, para conseguir recuperar os dados.
                            val logradouro = response.body()?.logradouro.toString()
                            val bairro = response.body()?.bairro.toString()
                            val localidade = response.body()?.localidade.toString()
                            val uf = response.body()?.uf.toString()
                            // aqui recuperamos todos os dados que desejamos. No caso o corpo dessa resposta.
                            // irei passar esses dados, para um outro metodo, para organizar melhor.
                            setFormularios(logradouro, bairro, localidade, uf)
                        }else{
                            Toast.makeText(applicationContext, "CEP invalido", Toast.LENGTH_LONG).show()
                        }
                    }
                    // nessa chamada, posso colocar o erro, para algum erro inesperado
                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        Toast.makeText(applicationContext, "Erro inesperado!", Toast.LENGTH_LONG).show()
                    }

                })
            }

        }

    }

    private fun setFormularios(logradouro: String, bairro: String, localidade: String, uf: String){
        //agora aqui, irei passar os dados para os Edit Text
        binding.editLogradouro.setText(logradouro)
        binding.editBairro.setText(bairro)
        binding.editCidade.setText(localidade)
        binding.editUf.setText(uf)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // nesse caso é para criar um botão de menu. Vou criar outra sobreescrita de metodos para limpar os dados na tela
        var inflate = menuInflater
        inflate.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //aqui coloquei a logica, para limpar os dados informados
        when(item.itemId){
            R.id.reset -> {
                val limparCep = binding.editCep
                val limparLogradouro = binding.editLogradouro
                val limparBairro = binding.editBairro
                val limparCidade = binding.editCidade
                val limparUf = binding.editUf

                limparCep.setText("")
                limparLogradouro.setText("")
                limparBairro.setText("")
                limparCidade.setText("")
                limparUf.setText("")

            }
        }

        return super.onOptionsItemSelected(item)
    }

}