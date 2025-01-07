package com.exercicis;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

/**
    Introducció
    -----------

    En aquests exàmen es farà un gestor de dades per una notaria.

    Hi haurà diferents tipus de dades, 'clients' i 'operacions'.

    Exemples de com han de ser les dades:

    clients = {
        "client_100": {
            "nom": "Joan Garcia", 
            "edat": 45, 
            "factors": ["autònom", "risc mitjà"], 
            "descompte": 15
        },
        "client_401": {"nom": "Marta Pérez", "edat": 38, "factors": ["empresa", "risc baix"], "descompte": 10},
        "client_202": {"nom": "Pere López",  "edat": 52, "factors": ["autònom", "risc alt"],  "descompte": 5}
    }

    operacions = [
        {
            "id": "operacio_100", 
            "tipus": "Declaració d'impostos", 
            "clients": ["client_100", "client_202"], 
            "data": "2024-10-05", 
            "observacions": "Presentació conjunta", 
            "preu": 150.0
        },
        {"id": "operacio_304", "tipus": "Gestió laboral",    "clients": ["client_202"], "data": "2024-10-04", "observacions": "Contractació de personal",   "preu": 200.0},
        {"id": "operacio_406", "tipus": "Assessoria fiscal", "clients": ["client_401"], "data": "2024-10-03", "observacions": "Revisió d'informes", "preu": 120.0}
    ]
*/

public class Exercici0 {

    // Variables globals (es poden fer servir a totes les funcions)
    public static HashMap<String, HashMap<String, Object>> clients = new HashMap<>();
    public static ArrayList<HashMap<String, Object>> operacions = new ArrayList<>();

    // Neteja la consola tenint en compte el sistema operatiu
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Valida si un nom és vàlid.
     * Un nom és vàlid si no està buit i només conté lletres o espais
     * com a mínim a de tenir dues lletres
     *
     * @param nom El nom a validar.
     * @return True si el nom és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarNom"
     */
    public static boolean validarNom(String nom) {
        nom = nom.toLowerCase();

        if (nom.isEmpty()) {
        return false;
        }

        if (!nom.matches("^[a-zA-Z]+$")) {
            return false;
        }

        int contadorL = 0;
        for (char c : nom.toCharArray()) {
            if (Character.isLetter(c)) {
                contadorL++;
            }
        }

        if (contadorL < 2) {
            return false;
        }

        return true; 
    }
    
    /**
     * Valida que l'edat sigui un valor vàlid.
     * Comprova que l'edat sigui un enter i que estigui dins del rang acceptable 
     * (entre 18 i 100, ambdós inclosos).
     *
     * @param edat L'edat que s'ha de validar.
     * @return True si l'edat es troba entre 18 i 100 (inclosos), false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarEdat"
     */
    public static boolean validarEdat(int edat) {

        if (edat < 18 || edat > 100) {
            return false;
        }
        
        return true;
    }

    /**
     * Valida que els factors proporcionats siguin vàlids.
     * Comprova que:
     * - Els factors siguin una llista amb exactament dos elements.
     * - El primer element sigui "autònom" o "empresa".
     * - El segon element sigui "risc alt", "risc mitjà" o "risc baix".
     * - Un "autònom" no pot ser de "risc baix".
     * 
     * Exemples:
     * validarFactors(new String[]{"autònom", "risc alt"})      // retorna true
     * validarFactors(new String[]{"empresa", "risc moderat"})  // retorna false
     *
     * @param factors Llista d'elements a validar.
     * @return True si els factors compleixen les condicions, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarFactors"
     */
    public static boolean validarFactors(String[] factors) {

        if (factors.length != 2) {
        return false;
        }

        if (factors[0].equals("autònom") && !factors[0].equals("empresa")) {
            return false; 
        }

        if (!factors[1].equals("risc alt") &&
            !factors[1].equals("risc mitjà") &&
            !factors[1].equals("risc baix")
            ) {
            return false; 
        }

        if (factors[0].equals("autònom") && factors[1].equals("risc baix")) {
        return false; 
        }
        return true;
    }

    /**
     * Valida que el descompte sigui un valor vàlid.
     * Comprova que:
     * - El descompte sigui un número vàlid (enter o decimal).
     * - El descompte es trobi dins del rang acceptable (entre 0 i 20, ambdós inclosos).
     *
     * Exemples:
     *  validarDescompte(15) retorna true
     *  validarDescompte(25) retorna false
     * 
     * @param descompte El valor del descompte a validar.
     * @return True si el descompte és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarDescompte"
     */
    public static boolean validarDescompte(double descompte) {

        if (descompte >= 0 && descompte <= 20) {
            return true;
        }

        return false;
    }

    /**
     * Valida que el tipus d'operació sigui vàlid.
     * Comprova que:
     * - El tipus d'operació proporcionat coincideixi amb algun dels tipus vàlids.
     * 
     * Els tipus vàlids inclouen:
     * "Declaració d'impostos", "Gestió laboral", "Assessoria fiscal",
     * "Constitució de societat", "Modificació d'escriptures",
     * "Testament", "Gestió d'herències", "Acta notarial",
     * "Contracte de compravenda", "Contracte de lloguer".
     *
     * Exemples:
     *  validarTipusOperacio("Declaració d'impostos") retorna true
     *  validarTipusOperacio("Operació desconeguda") retorna false
     * 
     * @param tipus El tipus d'operació a validar.
     * @return True si el tipus d'operació és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarTipusOperacio"
     */
    public static boolean validarTipusOperacio(String tipus) {

        String[] validos = {
            "Declaració d'impostos", "Gestió laboral", "Assessoria fiscal",
      "Constitució de societat", "Modificació d'escriptures",
      "Testament", "Gestió d'herències", "Acta notarial",
      "Contracte de compravenda", "Contracte de lloguer"
        };

        for ( String valido : validos) {
            if (valido.equals(tipus)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Valida que la llista de clients sigui vàlida.
     * Comprova que:
     * - La llista de clients sigui efectivament una llista (no `null`).
     * - Una llista buida és vàlida (sense cap element, és a dir de mida 0).
     * - Tots els elements de la llista de clients siguin únics.
     * - Tots els clients de la llista es trobin dins de la llista global de clients vàlids.
     *
     * Exemples:
     *  validarClients(new ArrayList<>(List.of("client1", "client2")), 
     *                 new ArrayList<>(List.of("client1", "client2", "client3"))) retorna true
     *  validarClients(new ArrayList<>(List.of("client1", "client1")), 
     *                 new ArrayList<>(List.of("client1", "client2", "client3"))) retorna false
     *  validarClients(new ArrayList<>(), 
     *                 new ArrayList<>(List.of("client1", "client2", "client3"))) retorna true
     * 
     * @param clientsLlista La llista de clients a validar.
     * @param clientsGlobals La llista global de clients vàlids.
     * @return True si la llista de clients compleix totes les condicions, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarClients"
     */
    public static boolean validarClients(ArrayList<String> clientsLlista, ArrayList<String> clientsGlobals) {

        if (clientsLlista == null) {
            return false;
        }
        
        HashSet<String> setCliente = new HashSet<>(clientsLlista);
        if (setCliente.size() != clientsLlista.size()) {
            return false;
        }


        for (String client : clientsLlista) {
            if (!clientsGlobals.contains(client)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprova si una cadena conté només dígits.
     * 
     * @param str La cadena a comprovar.
     * @return True si la cadena conté només dígits, false altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testIsAllDigits"
     */
    public static boolean isAllDigits(String str) {

        if (str == null || str.isEmpty()) {
            return false;
        }

        return str.matches("\\d+");
    }

    /**
     * Valida que la data sigui en un format vàlid (AAAA-MM-DD) i que representi una data possible.
     * Comprova que:
     * - La longitud de la cadena sigui exactament 10 caràcters.
     * - La cadena es pugui dividir en tres parts: any, mes i dia.
     * - Any, mes i dia estiguin formats per dígits.
     * - Any estigui en el rang [1000, 9999].
     * - Mes estigui en el rang [1, 12].
     * - Dia estigui en el rang [1, 31].
     * - Es compleixin les limitacions de dies segons el mes.
     *
     * Exemples:
     *  validarData("2023-04-15") retorna true
     *  validarData("2023-02-30") retorna false
     *  validarData("2023-13-01") retorna false
     *
     * @param data La cadena que representa una data en format 'AAAA-MM-DD'.
     * @return True si la data és vàlida, false altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarData"
     */
    public static boolean validarData(String data) {

        if (data == null || data.length() != 10) {
            return false;
        }

        String[] separador = data.split("-");

        if (separador.length != 3) {
            return false;
        }

        String any = separador[0];
        String mes = separador[1];
        String dia = separador[2];

        if (!any.matches("\\d{4}") || !mes.matches("\\d{2}") || !dia.matches("\\d{2}")) {
        return false;
        }

        //convertir a enteros para las comparaciones 
        int anyInt = Integer.parseInt(any);
        int mesInt = Integer.parseInt(mes);
        int diaInt = Integer.parseInt(dia);

        //verificacion del año dentro del rango
        if (anyInt < 1000 || anyInt > 9999) {
            return false;
        }

        //verificacion del mes dentro del rango 
        if (mesInt < 1 || mesInt > 12) {
            return false;
        }

        //verificacion del dia dentro del rango
        if (diaInt < 1 || diaInt > 31) {
            return false;
        }

        // verifiacion de la cantidad de dias segun el mes 

        if (!diaValido(anyInt, mesInt, diaInt)) {
            return false;
        }
        return true; 
    }

    public static boolean diaValido(int any, int mes, int dia) {
        
        // array con los numeros de dias de cada mes en orden
        int[] diasporMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        //comprobacion de si es bisiesto
        if(esBisiesto(any)) {
            diasporMes[1] = 29; //cambiamos los dias a [1] febrero.
        }

        return dia <= diasporMes[mes-1];
    }

    public static boolean esBisiesto(int any) {
        return (any % 4 == 0 && (any % 100 != 0 || any % 400 == 0));
    }

    /**
     * Valida que el preu sigui un número vàlid i superior a 100.
     * Comprova que:
     * - El preu sigui un número vàlid (decimal o enter).
     * - El valor del preu sigui estrictament superior a 100.
     *
     * Exemples:
     *  validarPreu(150.0) retorna true
     *  validarPreu(99.99) retorna false
     *  validarPreu(100.0) retorna false
     * 
     * @param preu El valor del preu a validar.
     * @return True si el preu és un número i és superior a 100, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarPreu"
     */
    public static boolean validarPreu(double preu) {

        if (preu > 100) {
            return true;
        }

        return false;
    }

    /**
     * Genera una clau única per a un client.
     * La clau és en el format "client_XYZ", on XYZ és un número aleatori entre 100 i 999.
     * Comprova que la clau generada no existeixi ja en el diccionari de clients.
     *
     * @return Una clau única per al client.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGeneraClauClient"
     */
    public static String generaClauClient() {

        Random random = new Random();
        String clave;

        do {
            int numeroAleatori = 100 + random.nextInt(999);
            //formato de la clave 
            clave = "client_"+numeroAleatori;
        } while (clients.containsKey(clave)); // verificamos que no hayan mas claves iguales

        return clave;
    }

    /**
     * Afegeix un nou client al diccionari de clients.
     * - Genera una nova clau amb "generaClauClient"
     * - Afegeix una entrada al diccionari de clients, 
     *   on la clau és la nova clau generada i el valor és un HashMap 
     *   amb el nom, edat, factors i descompte del nou client.
     *
     * Exemples:
     *  afegirClient(clients, "Maria", 30, new ArrayList<>(List.of("empresa", "risc baix")), 10) retorna "client_0"
     *
     * @param nom El nom del nou client.
     * @param edat L'edat del nou client.
     * @param factors La llista de factors associats al client.
     * @param descompte El descompte associat al nou client.
     * @return La clau del nou client afegit.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirClient"
     */
    public static String afegirClient(String nom, int edat, ArrayList<String> factors, double descompte) {

        String nuevaClave = generaClauClient();

        Map<String, Object> dadesClient = new HashMap<>();
        dadesClient.put("nom", nom);
        dadesClient.put("edat", edat);
        dadesClient.put("factors", factors);
        dadesClient.put("descompte", descompte);

        clients.put(nuevaClave, dadesClient);

        return nuevaClave;
    }

    /**
     * Modifica un camp específic d'un client al diccionari de clients.
     * - Comprova si la clau del client existeix al diccionari de clients.
     * - Si existeix, comprova si el camp que es vol modificar és vàlid (existeix dins del diccionari del client).
     * - Si el camp existeix, actualitza el valor del camp amb el nou valor.
     * - Si el camp no existeix, retorna un missatge d'error indicant que el camp no existeix.
     * - Si la clau del client no existeix, retorna un missatge d'error indicant que el client no es troba.
     * 
     * Retorn:
     * - Retorna "Client 'client_XYZ' no existeix." si el client no existeix
     * - Retorna "El camp 'campErroni' si el camp no existeix en aquest client
     * - "OK" si s'ha modificat el camp per aquest client
     * 
     * @param clauClient La clau del client que s'ha de modificar.
     * @param camp El camp que s'ha de modificar.
     * @param nouValor El nou valor que s'ha d'assignar al camp.
     * @return Un missatge d'error si el client o el camp no existeixen; "OK" altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarClient"
     */
    public static String modificarClient(String clauClient, String camp, Object nouValor) {

        // comprovar si el client existeix al diccionari
        if (!clients.containsKey(clauClient)) {
            return "Client '" + clauClient + "' no existeix.";

        }

        // obtenemos los datos del cliente
        Map<String, Object> dadesClient = clients.get(clauClient);

        if (!dadesClient.containsKey(camp)) {
            return "El camp '"+camp+"' no existeix en aquest client.";
        }

        dadesClient.put(camp, nouValor);

        return "OK";
    }

    /**
     * Esborra un client del diccionari de clients.
     * Comprova:
     * - Si la clau del client existeix dins del diccionari de clients.
     * - Si la clau del client existeix, elimina el client del diccionari.
     * - Si la clau del client no existeix, retorna un missatge d'error.
     *
     * Retorn:
     * - Si el client no existeix, retorna un missatge d'error: "Client amb clau {clauClient} no existeix."
     * - Si el client existeix, l'elimina i retorna "OK".
     *
     * @param clauClient La clau del client que s'ha d'esborrar.
     * @return Un missatge d'error si el client no existeix o "OK" si s'ha esborrat correctament.
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarClient"
     */
    public static String esborrarClient(String clauClient) {
        //comprovacio de si la clau existeix
        if (!clients.containsKey(clauClient)) {
            return "Client amb clau " + clauClient + " no existeix.";

        }

        //eliminem el client 
        clients.remove(clauClient);

        return "OK";
    }

    /**
     * Llista clients que compleixen determinades condicions.
     * Comprova si els clients que coincideixen amb les claus 
     * especificades compleixen les condicions proporcionades.
     * 
     * @param claus La llista de claus de clients a considerar per la cerca.
     * @param condicions Les condicions que els clients han de complir.
     * @return Una llista de diccionaris, on cada diccionari conté 
     *         la clau del client i les dades del client.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarClients"
     */
    public static ArrayList<HashMap<String, HashMap<String, Object>>> llistarClients(
            ArrayList<String> claus,
            HashMap<String, Object> condicions) {
        ArrayList<HashMap<String, HashMap<String, Object>>> result = new ArrayList<>();
        //comprovamos si el cliente existe
        for (String clau : claus) {
            if (clients.containsKey(clau)) {
                HashMap<String, Object> clientData = clients.get(clau); // obtenemos los datos del cliente

                boolean condicionsComplertes = true;

                for (String key : condicions.keySet()) {
                    Object valorCondicio = condicions.get(key);

                    if (!clientData.containsKey(key) || !clientData.get(key).equals(valorCondicio)) {
                        condicionsComplertes = false;
                        break;
                    }
                }

                if (condicionsComplertes) {
                    HashMap<String, HashMap<String, Object>> clientResult = new HashMap<>();
                    clientResult.put(clau, clientData);
                    result.add(clientResult);
                }
            }
        }

        return result;
    }

    /**
     * Genera una clau única per a una operació.
     * La clau és en el format "operacio_XYZ", on XYZ és un número aleatori entre 100 i 999.
     * Comprova que la clau generada no existeixi ja en la llista d'operacions.
     *
     * @return Una clau única per a l'operació.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGeneraClauOperacio"
     */
    public static String generaClauOperacio() {

        Random random = new Random();

        String clau;
        boolean clauExistent;

        do {
            int numeroAleatori = random.nextInt(999) + 100; 
            clau = "operacio_" + numeroAleatori;

            clauExistent = operacions.containsKey(clau);
        } while (clauExistent);
        

        return clau;
    }

    /**
     * Afegeix una nova operació a la llista d'operacions.
     * - Genera un nova clau amb "generaClauOperacio"
     * - Crea un HashMap que representa la nova operació amb els camps següents:
     *   - "id": clau de l'operació.
     *   - "tipus": el tipus d'operació.
     *   - "clients": llista de clients implicats.
     *   - "data": la data de l'operació.
     *   - "observacions": observacions de l'operació.
     *   - "preu": preu de l'operació.
     * - Afegeix aquest HashMap a la llista d'operacions.
     * 
     * @param tipus El tipus d'operació.
     * @param clientsImplicats La llista de clients implicats.
     * @param data La data de l'operació.
     * @param observacions Observacions addicionals sobre l'operació.
     * @param preu El preu associat a l'operació.
     * @return L'identificador de la nova operació.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirOperacio"
     */
    public static String afegirOperacio(
            String tipus,
            ArrayList<String> clientsImplicats,
            String data,
            String observacions,
            double preu) {

        String clauOperacio = generaClauOperacio();

        HashMap<String, Object> operacio = new HashMap<>();
        operacio.put("id", clauOperacio);
        operacio.put("tipus", tipus);
        operacio.put("clients", clientsImplicats);
        operacio.put("data", data);
        operacio.put("observacions", observacions);
        operacio.put("preu", preu);

        operacions.put(clauOperacio, operacio);

        return clauOperacio;
    }

    /**
     * Modifica un camp específic d'una operació dins de la llista d'operacions.
     * 
     * @param idOperacio L'identificador de l'operació que s'ha de modificar.
     * @param camp El camp específic dins del HashMap de l'operació que s'ha de modificar.
     * @param nouValor El nou valor que es vol assignar al camp especificat.
     * @return Un missatge d'error si l'operació o el camp no existeix, "OK" 
     *         si la modificació s'ha realitzat correctament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarOperacio"
     */
    public static String modificarOperacio(String idOperacio, String camp, Object nouValor) {

        // verificacion de si la operacion existe
        if (!operacions.containsKey(idOperacio)) {
            return "L'operació amb id '" + idOperacio + "' no existeix.";

        }

        // obtenems la operacion
        HashMap<String, Object> operacio = operacions.get(idOperacio);


        //verificar si el campo existe dentro de la operacion
        if (!operacio.containsKey(camp)) {
            return "El camp '" + camp + "' no existeix en l'operació.";

        }

        //actualizamos el valor del campo 
        operacio.put(camp, nouValor);

        return "Ok";
    }

    /**
     * Esborra una operació de la llista d'operacions basada en l'identificador de l'operació.
     * 
     * @param idOperacio L'identificador de l'operació que es vol esborrar.
     * @return Un missatge d'error si l'operació amb 'idOperacio' no existeix, "OK" si s'esborra correctament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarOperacio"
     */
    public static String esborrarOperacio(String idOperacio) {

        if (!operacions.containsKey(idOperacio)) {
            return "Loperació amb id '" + idOperacio + "' no existeix.";
        }

        operacions.remove(idOperacio);

        return "Ok";
    }

    /**
     * Llista les operacions que compleixen determinats criteris basats 
     * en identificadors i condicions específiques.
     * 
     * @param ids Una llista d'identificadors d'operacions que es volen considerar. 
     *            Si està buida, es consideren totes les operacions.
     * @param condicions Un HashMap amb les condicions que les operacions han de complir.
     * @return Una llista amb les operacions que compleixen les condicions.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarOperacions"
     */
    public static ArrayList<HashMap<String, Object>> llistarOperacions(
            ArrayList<String> ids,
            HashMap<String, Object> condicions) {

        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        HashMap<String, HashMap<String, Object>> operacions = getOperacions();

        //si id esta vacia consideramos todas las operaciones
        if (ids.isEmpty()) {
            ids.addAll(operacions.keySet());
        }

        //iteramos sobre ls id
        for (String id : ids) {
            HashMap<String, Object> operacio = operacions.get(id);
            if (operacio != null && compleixCondicions(operacio, condicions)) {
                result.add(operacio);
            }
        }

        return result;
    }

    private static boolean compleixCondicions(HashMap<String, Object> operacio, HashMap<String, Object> condicions) {
        for (Map.Entry<String, Object> entry : condicions.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!operacio.containsKey(key) || !operacio.get(key).equals(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Llista les operacions associades a un client específic basant-se en la seva clau.
     * 
     * @param clauClient La clau única del client que es vol filtrar.
     * @return Una llista amb les operacions associades al client especificat.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarOperacionsClient"
     */
    public static ArrayList<HashMap<String, Object>> llistarOperacionsClient(String clauClient) {

        //lista donde almacenaremos las operaciones del cliente
        ArrayList<HashMap<String, Object>> operacionsClient = new ArrayList<>();

        HashMap<String, HashMap<String, Object>> operacions = getOperacions();

        for (HashMap<String, Object> operacio : operacions.values()) {
            ArrayList<String> clients = (ArrayList<String>) operacio.get("clients");


            if (clients != null && clients.contains(clauClient)) {
                operacionsClient.add(operacio);
            }
        }

        return operacionsClient;
    }

    /**
     * Mètode que formata i alinea columnes de text 
     * segons les especificacions donades.
     * 
     * El mètode processa cada columna:
     * - Si el text és més llarg que l'amplada especificada, el trunca
     * - Afegeix els espais necessaris segons el tipus d'alineació:
     *   * "left": alinea el text a l'esquerra i omple amb espais a la dreta
     *   * "right": omple amb espais a l'esquerra i alinea el text a la dreta
     *   * "center": distribueix els espais entre esquerra i dreta per centrar el text
     * 
     * @param columnes ArrayList que conté arrays d'Objects, on cada array representa una columna amb:
     *                 - posició 0: String amb el text a mostrar
     *                 - posició 1: String amb el tipus d'alineació ("left", "right", "center")
     *                 - posició 2: int amb l'amplada total de la columna
     * 
     * @return String amb totes les columnes formatades concatenades
     * 
     * Per exemple:
     * Si input és: {{"Hola", "left", 6}, {"Mon", "right", 5}}
     * Output seria: "Hola    Mon"
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAlineaColumnes"
     */
    public static String alineaColumnes(ArrayList<Object[]> columnes) {

        StringBuilder resultat = new StringBuilder();

        for (Object[] columna : columnes) {
            String text = (String) columna[0];
            String alineacion = (String) columna[1];
            int amplada = (int) columna[2];
            
            // trucanr el texto en caso de ser mas largo que la amplitud
            if (text.length() > amplada) {
                text = text.substring(0, amplada);
            }

            //calculamos el numero de espacios
            int espais = amplada - text.length();

            switch (alineacion) {
                case "left": // alineamos a la izq 
                    text = String.format("%-"+amplada+"s",text);
                    break;
                case "right":
                    text = String.format("%-"+amplada+"s",text);
                    break;
                case "center":
                int leftPadding = espais / 2;
                int rightPadding = espais -leftPadding;
                text = " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
                break;
                
            }

            resultat.append(text);
        }

        return resultat.toString();
    }

    /**
     * Genera una representació en forma de taula de les operacions 
     * associades a un client específic.
     * 
     * Cada linia del resultat es guarda en un String de l'ArrayList.
     * 
     * Fes servir: Locale.setDefault(Locale.US)
     * 
     * Format esperat de sortida:
     * ```
Marta Puig i Puig, 45               [empresa, risc alt]
-------------------------------------------------------
Tipus                         Data                 Preu
Constitució de societat       2024-01-15        1250.50
Testament                     2024-02-28         750.75
Acta notarial                 2024-03-10         500.25
-------------------------------------------------------
                                          Suma: 2501.50
Descompte: 10%                            Preu: 2126.28
Impostos:  21% (85.05)                   Total: 2572.80

*******************************************************

Pere Vila, 25                    [estudiant, risc baix]
-------------------------------------------------------
Tipus                         Data                 Preu
Certificat                    2024-01-10          25.50
Fotocòpia                     2024-01-15          15.25
Segell                        2024-01-20          35.50
-------------------------------------------------------
                                            Suma: 76.25
Descompte: 10%                              Preu: 68.63
Impostos:  21% (14.41)                     Total: 83.04
     * ```
     * On:
     * - La primera línia mostra el nom, edat i factors del client
     * - Els tipus d'operació s'alineen a l'esquerra
     * - Les dates tenen format YYYY-MM-DD
     * - Els preus mostren sempre 2 decimals
     * - El descompte és un percentatge enter
     * - Els impostos són sempre el 21% del preu amb descompte
     *
     * @param clauClient La clau única del client per generar la taula d'operacions.
     * @param ordre El camp pel qual s'ordenaran les operacions (exemple: "data", "preu").
     * @return Una llista de cadenes de text que representen les línies de la taula.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient0"
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient1"
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient2"
     */
    public static ArrayList<String> taulaOperacionsClient(String clauClient, String ordre) {
        ArrayList<String> lineas = new ArrayList<>();

        try {
            Locale.setDefault(Locale.US);

            HashMap<String, Object> client = clients.get(clauClient);
            if (client == null) {
                lineas.add("Client amb clau "+clauClient+" no existeix.");
                return lineas;
            }

            ArrayList<HashMap<String, Object>> operacionsClient = llistarOperacionsClient(clauClient);
            operacionsClient.sort((o1, o2) -> {
                Object val1 = o1.get(ordre);
                Object val2 = o2.get(ordre);
                return val1.toString().compareTo(val2.toString());
            });

            String nomEdat = client.get("nom") + ", " + client.get("edat");
            String factors = "[" + String.join(", ", (ArrayList<String>) client.get("factors")) + "]";

            ArrayList<Object[]> columnesCapçalera = new ArrayList<>();
            columnesCapçalera.add(new Object[]{nomEdat, "left", 25});
            columnesCapçalera.add(new Object[]{nomEdat, "right", 30});
            lineas.add(alineaColumnes(columnesCapçalera));

            lineas.add("-".repeat(55));

            ArrayList<Object[]> columnesTitols = new ArrayList<>();
            columnesTitols.add(new Object[]{"Tipus", "left", 30});
            columnesTitols.add(new Object[]{"Data", "left", 10});
            columnesTitols.add(new Object[]{"Preu", "right", 15});
            lineas.add(alineaColumnes(columnesTitols));

            double sumaPreu = 0.0;
            for (HashMap<String, Object> operacio : operacionsClient) {
                ArrayList<Object[]> columnesOperacio = new ArrayList<>();
                columnesOperacio.add(new Object[]{operacio.get("tipus").toString(), "left", 30});
                columnesOperacio.add(new Object[]{operacio.get("data").toString(), "left", 10});

                double preu = ((Number) operacio.get("preu")).doubleValue();
                columnesOperacio.add(new Object[]{String.format("%.2f", preu), "right", 15});

                lineas.add(alineaColumnes(columnesTitols));
                sumaPreu += preu;
            }

            lineas.add("-".repeat(55));

            int descomptePercentatge = 10;
            double preuDescomptat = sumaPreu * ((100 - descomptePercentatge) / 100.0);
            double impostos = preuDescomptat * 0.21;
            double total = preuDescomptat + impostos;

            ArrayList<Object[]> columnesTotals = new ArrayList<>();
            columnesTotals.add(new Object[]{String.format("Suma: %.2f", sumaPreu), "right", 55});
            lineas.add(alineaColumnes(columnesTotals));

            ArrayList<Object[]> columnesDescompte = new ArrayList<>();
            columnesDescompte.add(new Object[]{String.format("Descompte: %d%%", descomptePercentatge), "left", 30});
            columnesDescompte.add(new Object[]{String.format("Preu: %.2f", preuDescomptat), "right", 25});
            lineas.add(alineaColumnes(columnesDescompte));

            ArrayList<Object[]> columnesImpostos = new ArrayList<>();
            columnesImpostos.add(new Object[]{String.format("Impostos: 21%% (%.2f)", impostos), "left", 30});
            columnesImpostos.add(new Object[]{String.format("Total: %.2f", total), "right", 25});
            lineas.add(alineaColumnes(columnesImpostos));

            lineas.add("*******************************************");
            
        } catch (Exception e) {
            lineas.add("Error: "+ e.getMessage());
        } finally {
            Locale.setDefault(defaultLocale);
        }

        return lineas;
    }

    /**
     * Genera el menú principal de l'aplicació de Gestió de Notaria.
     * 
     * Retorna una llista de cadenes de text que representen 
     * les opcions disponibles en el menú principal de l'aplicació.
     *
     * @return Una llista de cadenes de text amb les opcions del menú principal.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGetCadenesMenu"
     */
    public static ArrayList<String> getCadenesMenu() {
        String menuText = """
=== Menú de Gestió de Notaria ===
1. Afegir client
2. Modificar client
3. Esborrar client
4. Llistar clients
5. Afegir operació
6. Modificar operació
7. Esborrar operació
8. Llistar operacions
0. Sortir
            """;
        String[] lines = menuText.split("\\R");
        return new ArrayList<>(Arrays.asList(lines));
    }

    /**
     * Genera el menú amb la llista de clients.
     * 
     * Retorna una llista de cadenes de text que representen 
     * cada un dels clients de la llista.
     * - El primer text de la llista és així: "=== Llistar Clients ==="
     * - En cas de no haver-hi cap client afegeix a la llista de retorn "No hi ha clients per mostrar."
     *
     * @return Una llista de cadenes de text amb els clients.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarClientsMenu"
     */
    public static ArrayList<String> getLlistarClientsMenu() {

        ArrayList<String> resultat = new ArrayList<>();
        resultat.add("=== Llistar Clients ===");

        if (clients.isEmpty()) {
            resultat.add("No hi ha clients per mostrar.");
        } else {
            for (String clauClient : clients.keySet()) {
                HashMap<String, Object> client = clients.get(clauClient);
                String nom = client.get("nom").toString();
                int edat = (int) client.get("edat");
                resultat.add(String.format("%s, %d anys", nom, edat));
            }
        }

        return resultat;
    }

    /**
     * Escriu per consola cada element d'una llista en una línia nova.
     * 
     * @param llista La llista de linies a mostrar
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testDibuixarLlista"
     */
    public static void dibuixarLlista(ArrayList<String> llista) {

        for (String linea : llista) {
            System.out.println(linea);
        }

    }
    

    /**
     * Demana a l'usuari que seleccioni una opció i retorna l'opció transformada a una paraula clau si és un número.
     * 
     * Mostra el text: "Selecciona una opció (número o paraula clau): ".
     * - Si l'opció introduïda és un número vàlid, es transforma a les paraules clau corresponents segons el menú.
     * - Si l'opció són paraules clau vàlides, es retornen directament.
     *   Les paraules clau han d'ignorar les majúscules, minúscules i accents
     * - Si l'opció no és vàlida, mostra un missatge d'error i torna a preguntar fins que l'entrada sigui vàlida.
     *   "Opció no vàlida. Torna a intentar-ho."
     * 
     * Relació de números i paraules clau:
     *  1. "Afegir client"
     *  2. "Modificar client"
     *  3. "Esborrar client"
     *  4. "Llistar clients"
     *  5. "Afegir operació"
     *  6. "Modificar operació"
     *  7. "Esborrar operació"
     *  8. "Llistar operacions"
     *  0. "Sortir"
     * 
     * @return La cadena introduïda per l'usuari (número convertit a paraula clau o paraula clau validada).
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testObtenirOpcio"
     */
    public static String obtenirOpcio(Scanner scanner) {

        ArrayList<String> menu = getCadenesMenu();

        while (true) {
            System.out.println("Selecciona una opció(numero o paraula clau): ");
            String opcio = scanner.nextLine().trim();

            try {
                int indice = Integer.parseInt(opcio);
                if (indice == 0) {
                    return "sortir";

                } else if (indice > 0 && indice < menu.size() - 1) {
                    return menu.get(indice).substring(2).trim();
                }
            } catch (NumberFormatException e) {
            }

            String opcioNormalized = opcio.toLowerCase().replace("ó", "o");
            for (int i = 1; i < menu.size() - 1; i++) {
                String paraulaClau = menu.get(i).substring(3).trim();
                String paraulaClauNormalized = paraulaClau.toLowerCase().replace("ó", "o");
                if (paraulaClauNormalized.equals(opcioNormalized)) {
                    return paraulaClau;
                }
            }

            System.out.println("Opció no vàlida. Torna a intentar-ho.");
        }

    }

    /**
     * Demana i valida el nom d'un client.
     * Mostra el missatge "Introdueix el nom del client: " i valida que el nom sigui correcte.
     * Si el nom no és vàlid, afegeix el missatge d'error a la llista i torna a demanar el nom.
     * Fes servir la funció "validarNom"
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return El nom validat del client
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirNom"
     */
    public static String llegirNom(Scanner scanner) {

        System.out.print("Introdueix el nom del client:");
        String nom = scanner.nextLine();

        while (!nom.matches("[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)*")) {
            System.out.println("Nom no valid. Nomes s'accepten lletres i espais.");
            System.out.print("Introdueix el nom del client: ");
            nom = scanner.nextLine().trim();
        }

        return nom;
    }

    /**
     * Demana i valida l'edat d'un client.
     * Mostra el missatge "Introdueix l'edat del client (18-100): " i valida que l'edat sigui correcta.
     * Si l'edat no és vàlida, afegeix el missatge d'error a la llista i torna a demanar l'edat.
     * Fes servir les funcions "isAllDigits" i "validarEdat"
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return L'edat validada del client
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirEdat"
     */
    public static int llegirEdat(Scanner scanner) {

        System.out.print("Introdueix l'edat del client (18-100): ");
        String edatStr = scanner.nextLine();

        while (!isAllDigits(edatStr) || !validarEdat(Integer.parseInt(edatStr))) {
            System.out.println("Edat no valida. Ha de ser un numero entre 18 i 100.");
            System.out.println("Introdueix l'edat del client (18-100): ");
            edatStr = scanner.nextLine().trim();
        }

        return Integer.parseInt(edatStr);
    }
    
    /**
     * Demana i valida els factors d'un client.
     * Primer demana el tipus de client (autònom/empresa) i després el nivell de risc.
     * Per autònoms, només permet 'risc alt' o 'risc mitjà'.
     * Per empreses, permet 'risc alt', 'risc mitjà' o 'risc baix'.
     * 
     * Mostra els següents missatges:
     * - "Introdueix el primer factor ('autònom' o 'empresa'): "
     * - Per autònoms: "Introdueix el segon factor ('risc alt' o 'risc mitjà'): "
     * - Per empreses: "Introdueix el segon factor ('risc alt', 'risc baix' o 'risc mitjà'): "
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return ArrayList amb els dos factors validats
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirFactors"
     */
    public static ArrayList<String> llegirFactors(Scanner scanner) {

        ArrayList<String> factors = new ArrayList<>();

        System.out.print("Introdueix el primer factor ('autònom' o 'empresa'): ");
        String tipus = scanner.nextLine().trim().toLowerCase();

        while(!tipus.equals("autònom") && !tipus.equals("empresa")) {
            System.out.println("Tipus no valid. Introdueix 'autònom' o 'empresa'.");
            System.out.print("Introdueix el primer factor ('autònom' o 'empresa'): ");
            tipus = scanner.nextLine().trim().toLowerCase();
        }
        factors.add(tipus);

        System.out.print(tipus.equals("autònom") ? "Introdueix el segon factor ('risc alt' o 'risc mitjà'): "
                                                    : "Introdueix el segon factor ('risc alt', 'risc baix' o 'risc mitjà'): ");
        String risc = scanner.nextLine().trim().toLowerCase();

        while (!(tipus.equals("autònom") && (risc.equals("risc alt") || risc.equals("ris mitjà"))) &&
                !(tipus.equals("empresa") && (risc.equals("risc alt") || risc.equals("risc mitjà") || risc.equals("risc baix")))) {
                    System.out.println("Risc no valid. Introdueix els valors valids segons el tipus de client.");
                    System.out.print(tipus.equals("autònom") ? "Introdueix el segon factor ('risc alt' o 'risc mitjà'): "
                                                            : "Introdueix el segon factor ('risc alt', 'risc baix' o 'risc mitjà'): ");
                    risc = scanner.nextLine().trim().toLowerCase();
                }
                factors.add(risc);
        return factors;
    }
    
    /**
     * Demana i valida un descompe
     * Primer demana el descompte amb: 
     * "Introdueix el descompte (0-20): "
     * 
     * Mostra el següent missatge en cas d'error: 
     * "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return ArrayList amb els dos factors validats
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirDescompte"
     */
    public static double llegirDescompte(Scanner scanner) {
        
        System.out.print("Introdueix el descompte (0-20): ");
        String descompteInput = scanner.nextLine().trim();

        while (!isAllDigits(descompteInput) || !validarDescompte(Integer.parseInt(descompteInput))) {
            System.out.println("Descompte no valid. Ha de ser un numero entre 0 i 20.");
            System.out.print("Introdueix el descompte (0-20): ");
            descompteInput = scanner.nextLine().trim();
        }

        return Double.parseDouble(descompteInput);
    }

    /**
     * Gestiona el procés d'afegir un nou client mitjançant interacció amb l'usuari.
     * Utilitza les següents funcions auxiliars per obtenir i validar les dades:
     * - llegirNom: per obtenir el nom del client
     * - llegirEdat: per obtenir l'edat (entre 18 i 100)
     * - llegirFactors: per obtenir el tipus (autònom/empresa) i nivell de risc
     * - llegirDescompte: per obtenir el descompte (entre 0 i 20)
     * 
     * La primera línia del retorn sempre és "=== Afegir Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn per les funcions auxiliars:
     * - "Nom no vàlid. Només s'accepten lletres i espais."
     * - "Edat no vàlida. Introdueix un número entre 18 i 100."
     * - "Factor no vàlid. Ha de ser 'autònom' o 'empresa'."
     * - "Factor no vàlid. Per a autònoms només pot ser 'risc alt' o 'risc mitjà'."
     * - "Factor no vàlid. Ha de ser 'risc alt', 'risc baix' o 'risc mitjà'."
     * - "Els factors no són vàlids."
     * - "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha afegit el client amb clau " + novaClau + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari
     * @return Una llista de cadenes de text que contenen els missatges d'estat del procés
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirClientMenu"
     */
    public static ArrayList<String> afegirClientMenu(Scanner scanner) {

        ArrayList<String> linies = new ArrayList<>();
        linies.add("=== Afegir Client ===");

        String nom = llegirNom(scanner);
        int edat = llegirEdat(scanner);
        ArrayList<String> factors = llegirFactors(scanner);

        if (!validarFactors(factors.toArray(new String[0]))) {
            linies.add("Els factors no son valids.");
            return linies;
        }

        double descompte = llegirDescompte(scanner);

        String novaClau = afegirClient(nom, edat, factors, descompte);
        linies.add("S'ha afegit el client amb clau " + novaClau + ".");
        return linies;
    }
    
    /**
     * Gestiona el procés de modificació d'un client existent.
     * 
     * Primer demana i valida la clau del client:
     * - "Introdueix la clau del client a modificar (per exemple, 'client_100'): "
     * 
     * Si el client existeix:
     * - Mostra "Camps disponibles per modificar: nom, edat, factors, descompte"
     * - Demana "Introdueix el camp que vols modificar: "
     * 
     * Segons el camp escollit, utilitza les funcions auxiliars:
     * - llegirNom: si es modifica el nom
     * - llegirEdat: si es modifica l'edat
     * - llegirFactors: si es modifiquen els factors
     * - llegirDescompte: si es modifica el descompte
     * 
     * La primera línia del retorn sempre és "=== Modificar Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn:
     * - "Client amb clau " + clauClient + " no existeix."
     * - "El camp " + camp + " no és vàlid."
     * 
     * Més els missatges d'error de les funcions auxiliars:
     * - "Nom no vàlid. Només s'accepten lletres i espais."
     * - "Edat no vàlida. Introdueix un número entre 18 i 100."
     * - "Factor no vàlid. Ha de ser 'autònom' o 'empresa'."
     * - "Factor no vàlid. Per a autònoms només pot ser 'risc alt' o 'risc mitjà'."
     * - "Factor no vàlid. Ha de ser 'risc alt', 'risc baix' o 'risc mitjà'."
     * - "Els factors no són vàlids."
     * - "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha modificat el client " + clauClient + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari
     * @return Una llista de cadenes de text que contenen els missatges d'estat del procés
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarClientMenu"
     */
    public static ArrayList<String> modificarClientMenu(Scanner scanner) {

        ArrayList<String> linies = new ArrayList<>();
        linies.add("=== Modificar Client ===");

        System.out.print("Introdueix la clau del client a modificar (per exemple, 'clau_100'): ");
        String clauClient = scanner.nextLine().trim();
        if (!clients.containsKey(clauClient)) {
            linies.add("Client amb clau " + clauClient + " no existeix.");
            return linies;
        }

        linies.add("Camps disponibles per modificar: nom, edat, factors, descompte");
        System.out.print("Introdueix el camp que vols modificar: ");
        String camp = scanner.nextLine().trim();
        if (!Arrays.asList("nom", "edat", "factors", "descompte").contains(camp)) {
            linies.add("El camp " + camp + " no es valid.");
            return linies;
        }

        Object nouValor = null;
        switch (camp) {
            case "nom":
                nouValor = llegirNom(scanner);
                break;
            case "edat":
                nouValor = llegirEdat(scanner);
                break;
            case "factors":
                nouValor = llegirFactors(scanner);
                break;
            case "descompte":
                nouValor = llegirDescompte(scanner);
                break;
        }

        if (nouValor == null) return linies;

        String resultat = modificarClient(clauClient, camp, nouValor);
        if (!resultat.equals("OK")) {
            linies.add(resultat);
        } else {
            linies.add("S'ha modificat el client " + clauClient + ".");
        }

        return linies;
    }

    /**
     * Gestiona el procés d'esborrar un client existent mitjançant interacció amb l'usuari.
     * 
     * Mostra per pantalla el següent missatge per demanar dades:
     * - "Introdueix la clau del client a esborrar (per exemple, 'client_100'): "
     * 
     * La primera línia del retorn sempre és "=== Esborrar Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn:
     * - "Client amb clau " + clauClient + " no existeix."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha esborrat el client " + clauClient + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari.
     * @return Una llista de cadenes de text que contenen els missatges d'estat del procés.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarClientMenu"
     */
    public static ArrayList<String> esborrarClientMenu(Scanner scanner) {

        ArrayList<String> linies = new ArrayList<>();
        linies.add("=== Esborrar Client === ");

        System.out.print("Introdueix la clau del client a esborrar (per exemple, 'client_100'): ");
        String clauClient = scanner.nextLine().trim();

        if (!clients.containsKey(clauClient)) {
            linies.add("Client amb clau " + clauClient + " no existeix.");
            return linies;
        }

        clients.remove(clauClient);
        linies.add("S'ha esborrat el client " + clauClient + ".");

        return linies;
    }

    /**
     * Gestiona el menú principal de l'aplicació i l'execució de les operacions.
     *
     * Aquesta funció implementa un bucle que:
     * 1. Mostra el menú principal.
     * 2. Mostra els missatges d'error o avís
     * 3. Obté l'opció seleccionada per l'usuari.
     * 4. Executa l'acció corresponent utilitzant les funcions existents.
     * 5. Finalitza quan l'usuari selecciona "Sortir".
     *
     * Els textos mostrats són:
     * - "=== Menú de Gestió de Notaria ==="
     * - "Selecciona una opció (número o paraula clau): "
     * - "Opció no vàlida. Torna a intentar-ho."
     * - "Fins aviat!"
     *
     * @param scanner L'objecte Scanner per llegir l'entrada de l'usuari.
     */
    public static void gestionaClientsOperacions(Scanner scanner) {

        ArrayList<String> menu = getCadenesMenu();
        ArrayList<String> resultat = new ArrayList<>();

        while (true) {
            clearScreen();
            dibuixarLlista(menu);
            dibuixarLlista(resultat);

            String opcio = obtenirOpcio(scanner);

            switch (opcio.toLowerCase(Locale.ROOT)) {
                case "sortir":
                    dibuixarLlista(new ArrayList<>(List.of("Fins aviat!")));
                    return;
                case "afegir client":
                    resultat = afegirClientMenu(scanner);
                    break;
                case "modificar client":
                    resultat = modificarClientMenu(scanner);    
                    break;
                case "esborrar client":
                    resultat = esborrarClientMenu(scanner);
                    break;
                case "llistar clients":
                    resultat = getLlistarClientsMenu();
                    break;
                default:
                    resultat = new ArrayList<>(List.of("Opcio no valida. Torna a intentar-ho"));
            }
        }

    }

    /**
     * 
     * @run ./run.sh "com.exercicis.Exercici0"
     * @test ./runTest.sh "com.exercicis.TestExercici0"
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        gestionaClientsOperacions(scanner);

        scanner.close();
    }
}
