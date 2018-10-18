public class Ficha {
	private String id;
	private String color;
	private int valor;
	private int posicionFila;
	private int posicionColumna;
	
	public Ficha(String id, int valor, String color) {
		this.id = id;
		this.valor = valor;
		this.color = color;
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getColor() {
		return this.color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getValor() {
		return valor;
	}


	public void setValor(int valor) {
		this.valor = valor;
	}


	public int getPosicionFila() {
		return posicionFila;
	}


	public void setFila(int posicionFila) {
		this.posicionFila = posicionFila;
	}


	public int getColumna() {
		return posicionColumna;
	}


	public void setColumna(int posicionColumna) {
		this.posicionColumna = posicionColumna;
	}


	@Override
	public String toString() {
		String idaux =" ";
		if(this.getColor().equals("Azul")){
			idaux= "\033[34m"+id+"\033[0;37m";
		}else {
			idaux= "\033[31m"+id+"\033[0;37m";
		}

		return idaux;
	}
}
