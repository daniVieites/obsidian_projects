import java.util.*;

public class Ejercicio3 {
  int objetivo;
  List<String> prohibidos = new ArrayList<>();
  List<Set<Integer>> validados = new ArrayList<>();
  Arbol arbol;

  public static void main(String[] args) {
    Ejercicio3 ejercicio3 = new Ejercicio3();
    List<String> casos = ejercicio3.obtenerCasos();

    casos.forEach(caso -> {
      List<String> valores = Arrays.asList(caso.split(" "));
      ejercicio3.objetivo = Integer.parseInt(valores.get(0));
      ejercicio3.prohibidos = valores.subList(1, valores.size());
      ejercicio3.validados.clear();
      ejercicio3.arbol = new Arbol();
      System.out.print("Proceso:");
      ejercicio3.calcular(new Nodo());
      System.out.println("Resultado: " + ejercicio3.validados.size() + "\n");
    });
  }

  public void calcular(Nodo nodo){
    int dato = nodo.getDato();
    int suma = arbol.getSuma();
    if(arbol.getCabeza() != null && arbol.getCabeza() == nodo){
      if(dato > objetivo / 2){
        return;
      }else{
        calcular(new Nodo());
      }
    }else if(suma >= objetivo){
      if(suma == objetivo){
        addOperation();
      }
      nodo = retroceder();
      calcular(nodo);
    }else if(dato == 0){
      dato = siguienteValor(dato);
      nodo.setDato(dato);
      arbol.addNodo(nodo);
      calcular(new Nodo());
    }else{
      calcular(new Nodo());
    }
  }

  public void addOperation() {
    boolean add = true;
    Set<Integer> nodos = arbol.getNodos();
    for(Set<Integer> set : validados){
      if(set.equals(nodos)){
        add = false;
        break;
      }
    }
    if(add){
      System.out.println("+1");
      validados.add(nodos);
    }
  }

  public Nodo retroceder(){
    Nodo nodo = arbol.getCola().getPadre();
    arbol.setCola(nodo);
    nodo.setHijo(null);
    int dato = siguienteValor(nodo.getDato());
    nodo.setDato(dato);
    return nodo;
  }

  public int siguienteValor(int dato){
    do{
      ++dato;
    }while(prohibidos.contains(dato + ""));
    return dato;
  }

  public List<String> obtenerCasos(){
    Scanner sc = new Scanner(System.in);
    List<String> casos = new ArrayList<>();

    System.out.println("Escribe 0 cuando quieras dejar de insertar casos");
    while(true){
      System.out.print("Nuevo caso: ");
      String caso = sc.nextLine().trim();
      if(caso.matches("0")) {
        break;
      }
      casos.add(caso);
    }
    sc.close();
    return casos;
  }
}

class Arbol{
  private Nodo cabeza;
  private Nodo cola;

  public void addNodo(Nodo nodo){
    if(cabeza == null){
      cabeza = nodo;
      cola = nodo;
    }else{
      cola.setHijo(nodo);
      nodo.setPadre(cola);
      cola = nodo;
    }
  }

  public Set<Integer> getNodos(){
    Set<Integer> set = new TreeSet<>();
    Nodo actual = cabeza;
    do{
      set.add(actual.getDato());
      actual = actual.getHijo();
    }while(actual != null);
    return set;
  }
  
  public int getSuma(){
    int suma = 0;
    Nodo actual = cabeza;
    while(actual != null){
      System.out.print(actual.getDato() + " ");
      suma += actual.getDato();
      actual = actual.getHijo();
    }
    System.out.println("");
    return suma;
  }

  public Nodo getCabeza(){
    return cabeza;
  }

  public Nodo getCola(){
    return cola;
  }

  public void setCola(Nodo nodo){
    this.cola = nodo;
  }
}

class Nodo{
  private Nodo padre;
  private int dato;
  private Nodo hijo;

  public Nodo(){
    this.dato = 0;
  }

  public Nodo getPadre() {
    return padre;
  }

  public void setPadre(Nodo padre) {
    this.padre = padre;
  }

  public int getDato() {
    return dato;
  }

  public void setDato(int dato) {
    this.dato = dato;
  }

  public Nodo getHijo() {
    return hijo;
  }

  public void setHijo(Nodo hijo) {
    this.hijo = hijo;
  }
}