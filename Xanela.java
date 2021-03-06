import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Xanela extends JFrame
{
    private MainAgent meuAxente;
    private int tamanhoMatriz = 5, rondasPartidaTotal = 10000, rondasCambio = 100, porcentaxeCambio = 25;

    /* Paneis de texto */
    private JTextArea panelMatriz = new JTextArea();
    private JTextArea panelInformacion = new JTextArea();
    private JTextArea panelClasificacion = new JTextArea();

    /* Scrolls para ver o contido dos paneis */
    private JScrollPane scrollMatriz = new JScrollPane(this.panelMatriz);
    private JScrollPane scrollInformacion = new JScrollPane(this.panelInformacion);
    private JScrollPane scrollClasificacion = new JScrollPane(this.panelClasificacion);

    /* Botóns */
    private JButton botonNovoXogo = new JButton("NOVO XOGO");
    private JButton botonPuasa = new JButton("PAUSAR");
    private JButton botonContinuar = new JButton("CONTINUAR");

    /* Labels */
    private JLabel labelRondas = new JLabel();
    private JLabel labelPorcentaxe = new JLabel();

    public Xanela(MainAgent meuAxente) {
        setTitle("Práctica B");
        this.meuAxente = meuAxente;
        /* Estabelecemos a Xanela para que ocupè todo o monitor */
        Dimension screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenDimension.getWidth()), (int) (screenDimension.getHeight() * 0.95));

        getContentPane().setBackground(new Color(25, 178, 172));

        setResizable(false); //Evitamos que o usuario poida cambiar o tamaño da xanela
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(null);

        crearMenu();
        crearPanelMatriz();
        crearPanelInformacion();
        crearPanelClasificacion();
        crearBotonNovoXogo();
        crearBotonPausa();
        crearBotonContinuar();
        crearLabels();

    }

    public void crearMenu() {
        //Creamos os menus despregabres
        JMenu opcions = new JMenu("Opcións");
        JMenu axuda = new JMenu("Axuda");

        JMenuBar barraMenu = new JMenuBar();

        //Creamos os items do submenu "ǘERBOSE"
        JRadioButtonMenuItem verboseOff = new JRadioButtonMenuItem("OFF", true);
        JRadioButtonMenuItem verboseON = new JRadioButtonMenuItem("ON", false);
        ButtonGroup grupoVerbose = new ButtonGroup();
        grupoVerbose.add(verboseOff);
        grupoVerbose.add(verboseON);

        JMenu verbose = new JMenu("Verbose");
        verbose.add(verboseOff);
        verbose.add(verboseON);

        //Activamos o modo verbose
        verboseON.addActionListener( (ActionEvent e) -> {
            this.meuAxente.setVerboseMode(true);
        });

        //Desactivamos o modo verbose
        verboseOff.addActionListener( (ActionEvent e) -> {
            this.meuAxente.setVerboseMode(false);
        });

        opcions.add(verbose);

        //Creamos os items do submenu "Velocidade"
        JRadioButtonMenuItem lento = new JRadioButtonMenuItem("lento", false);
        JRadioButtonMenuItem medio = new JRadioButtonMenuItem("medio", false);
        JRadioButtonMenuItem rapido = new JRadioButtonMenuItem("rapido", true);
        ButtonGroup grupoVelocidade = new ButtonGroup();
        grupoVelocidade.add(lento);
        grupoVelocidade.add(medio);
        grupoVelocidade.add(rapido);

        JMenu velocidade = new JMenu("Velocidade");
        velocidade.add(lento);
        velocidade.add(medio);
        velocidade.add(rapido);

        //Activamos o modo verbose
        lento.addActionListener( (ActionEvent e) -> {
            this.meuAxente.setVelocidade(1000);
        });

        //Desactivamos o modo verbose
        medio.addActionListener( (ActionEvent e) -> {
            this.meuAxente.setVelocidade(500);
        });

        rapido.addActionListener( (ActionEvent e) -> {
            this.meuAxente.setVelocidade(0);
        });

        opcions.add(velocidade);

        JMenuItem sobre = new JMenuItem("Sobre");
        axuda.add(sobre);

        sobre.addActionListener( (ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Nome: Sergio Simons Casasnovas\nDNI: 39486927K\nConta laboratorio: psi?", "Informaci\u00f3n sobre o creador", JOptionPane.INFORMATION_MESSAGE, null);
        });

        //Creamos os items do menu execucion e os anhadimos o menu despregabre
        JMenuItem configuracion = new JMenuItem("Configuraci\u00f3n");
        opcions.add(configuracion);


        //Creamos a xanela de configuracion
        configuracion.addActionListener( (ActionEvent e) -> {
            new xanelaConfiguracion();
        });

        barraMenu.add(opcions);
        barraMenu.add(Box.createHorizontalGlue()); // Pomos o boton axuda a dereita
        barraMenu.add(axuda);
        setJMenuBar(barraMenu);
    }

    public void crearPanelMatriz() {
        Font fonte = new Font("Dialog", Font.BOLD, 20);
        this.panelMatriz.setFont(fonte);
        this.panelMatriz.setBackground(new Color(255, 255, 255));
        this.panelMatriz.setEditable(false); // Evitamos que se poda escribir no panel
        this.scrollMatriz.setBounds(680, 100, 589, 460);
        add(this.scrollMatriz);
    }

    public void imprimirMatriz(String matrizPraImprimir) {
        //Pomos a matriz no seu recadro
        this.panelMatriz.setText(matrizPraImprimir);
    }

    public void crearPanelInformacion() {
        Font fonte = new Font("Dialog", Font.ITALIC, 15);
        this.panelInformacion.setFont(fonte);
        this.panelInformacion.setBackground(new Color(255, 255, 255));
        this.panelInformacion.setEditable(false); // Evitamos que se poda escribir no panel
        this.scrollInformacion.setBounds(50, 100, 489, 460);
        add(this.scrollInformacion);
    }

    public void imprimirInformacion(String informacionPraImprimir) {
        this.panelInformacion.append(informacionPraImprimir);
    }

    public void crearPanelClasificacion() {
        Font fonte = new Font("Dialog", Font.BOLD, 10);
        this.panelClasificacion.setFont(fonte);
        this.panelClasificacion.setBackground(new Color(255, 255, 255));
        this.panelClasificacion.setEditable(false); // Evitamos que se poda escribir no panel
        this.scrollClasificacion.setBounds(1383, 100, 489, 460);
        add(this.scrollClasificacion);
    }

    public void imprimirClasificacion(String clasificacionPraImprimir) {
        this.panelClasificacion.setText(clasificacionPraImprimir);
    }

    public void crearBotonNovoXogo() {
        int x = 400;
        int y = 810;
        int anchura = 400;
        int altura = 80;
        this.botonNovoXogo.setBounds(x, y, anchura, altura);
        add(this.botonNovoXogo);
        this.botonNovoXogo.addActionListener( (ActionEvent e) -> { // Engadimos a resposta do boton "Novo xogo"
            ArrayList<Xogador> xogadores = this.meuAxente.buscarAxentes();
            PopupXogadores meuPopup = new PopupXogadores(this.meuAxente, xogadores, this, true);
            meuPopup.setVisible(true);
        });
    }

    public void crearBotonPausa() {
        int x = 800;
        int y = 810;
        int anchura = 400;
        int altura = 80;
        this.botonPuasa.setBounds(x, y, anchura, altura);
        add(this.botonPuasa);
        this.botonPuasa.addActionListener( (ActionEvent e) -> { // Engadimos a resposta do boton "Novo xogo"
            this.meuAxente.setPausar(true);
        });
    }


    public void crearBotonContinuar() {
        int x = 1200;
        int y = 810;
        int anchura = 400;
        int altura = 80;
        this.botonContinuar.setBounds(x, y, anchura, altura);
        add(this.botonContinuar);
        this.botonContinuar.addActionListener( (ActionEvent e) -> { // Engadimos a resposta do boton "Novo xogo"
            this.meuAxente.setPausar(false);
        });
    }

    public void crearLabels() {
        this.labelRondas.setBounds(670, 620, 320, 30);
        this.labelPorcentaxe.setBounds(670, 665, 320, 30);
        add(this.labelRondas);
        add(this.labelPorcentaxe);
    }

    public void imprimirRonda(int ronda, int rondas) {
        this.labelRondas.setText("Ronda: " + ronda + "/" + rondas);
    }

    public void imprimirPorcentaxe(int porcentaxe) {
        this.labelPorcentaxe.setText("Porcentaxe de cambio da matríz: " + porcentaxe + "%");
    }

    private class PopupXogadores extends JDialog {
        private MainAgent meuAxente;
        private ArrayList<Xogador>  xogadores;
        private ArrayList<Xogador>  xogadoresElexidos = new ArrayList<>();
        private JCheckBox[] checkBoxes;
        private JButton botonAceptar = new JButton("ACEPTAR");

        public PopupXogadores(MainAgent meuAxente,ArrayList<Xogador>  xogadores, JFrame jFrame, boolean bool) {
            super(jFrame, bool);
            this.meuAxente = meuAxente;
            setTitle("Xogadores");
            this.xogadores = xogadores;
            this.checkBoxes = new JCheckBox[xogadores.size()];

            setSize(200,  95 + 30 * this.checkBoxes.length);
            setLocationRelativeTo(null);
            getContentPane().setBackground(new Color(25, 178, 172));

            setResizable(false); //Evitamos que o usuario poida cambiar o tamaño da xanela

            setLayout(null);

            crearCheckBoxes();
            crearBotonAceptar();
        }

        private void crearCheckBoxes() {
            for (int i = 0; i < this.checkBoxes.length; i++)
            {
                this.checkBoxes[i] = new JCheckBox(xogadores.get(i).aid.getLocalName());
                this.checkBoxes[i].setBackground(new Color(25, 178, 172));
                this.checkBoxes[i].setSelected(true);
                this.checkBoxes[i].setBounds(10, 10 + 30 * i, 180, 30);
                add(this.checkBoxes[i]);
            }
        }

        private void crearBotonAceptar() {
            int x = 50;
            int y = checkBoxes.length*30+20;
            int anchura = 100;
            int altura = 20;
            this.botonAceptar.setBounds(x, y, anchura, altura);
            add(this.botonAceptar);
            this.botonAceptar.addActionListener( (ActionEvent e) -> { // Engadimos a resposta do boton "Novo xogo"
                obterXogadoresElexidos();
                this.meuAxente.getParametros().setTamanhoMatriz(tamanhoMatriz);
                this.meuAxente.getParametros().setNumeroRondas(rondasPartidaTotal);
                this.meuAxente.getParametros().setIteracionsCambioMatriz(rondasCambio);
                this.meuAxente.getParametros().setPorcentaxeCambioMatriz(porcentaxeCambio);
                this.meuAxente.iniciarXogo(xogadoresElexidos);
                dispose();
            });
        }

        private void obterXogadoresElexidos() {
            for (int i = 0; i < this.checkBoxes.length; i++)
                if (this.checkBoxes[i].isSelected()) xogadoresElexidos.add(xogadores.get(i));
        }
    }

    /**
     * Clase que conten a xanela de configuracion
     */
    private class xanelaConfiguracion extends JDialog
    {
        //Etiquetas e lista
        private JLabel labelTamanoMatriz = new JLabel("Tamaño da matríz");
        private JLabel labelNumeroRondas = new JLabel("Número de rondas dunha partida");
        private JLabel labelRondasCambio = new JLabel("Número de rondas para cambiala matríz (0 para non cambiala)");
        private JLabel labelPorcentaxe = new JLabel("Porcentaxe de cambio da matriz");
        private ArrayList<JLabel> labels = new ArrayList<>();

        //Textos e lista
        private JTextArea areaTamanoMatriz = new JTextArea(String.valueOf(tamanhoMatriz));
        private JTextArea areaNumeroRondas = new JTextArea(String.valueOf(rondasPartidaTotal));
        private JTextArea areaRondasCambio = new JTextArea(String.valueOf(rondasCambio));
        private JTextArea areaPorcentaxe = new JTextArea(String.valueOf(porcentaxeCambio));
        private ArrayList<JTextArea> textAreas = new ArrayList<>();

        //Botons
        private JButton botonAceptar = new JButton("Aceptar");
        private JButton botonCancelar = new JButton("Cancelar");
        private ArrayList<JButton> botons = new ArrayList<>();

        public xanelaConfiguracion()
        {
            //Anhadimos os elementos na lista correspondente
            this.labels.add(this.labelTamanoMatriz);
            this.labels.add(this.labelNumeroRondas);
            this.labels.add(this.labelRondasCambio);
            this.labels.add(this.labelPorcentaxe);

            this.textAreas.add(this.areaTamanoMatriz);
            this.textAreas.add(this.areaNumeroRondas);
            this.textAreas.add(this.areaRondasCambio);
            this.textAreas.add(this.areaPorcentaxe);

            this.botons.add(this.botonAceptar);
            this.botons.add(this.botonCancelar);

            crearLayout();

            //Comprobamos e gardamos os datos, posteriormente, pechamos a xanela
            botonAceptar.addActionListener( (ActionEvent e) -> {
                try
                {
                    tamanhoMatriz = Integer.parseInt(this.areaTamanoMatriz.getText());
                    rondasPartidaTotal = Integer.parseInt(this.areaNumeroRondas.getText());
                    rondasCambio = Integer.parseInt(this.areaRondasCambio.getText());
                    porcentaxeCambio = Integer.parseInt(this.areaPorcentaxe.getText());
                    JOptionPane.showMessageDialog(null, "Os datos actualizaranse cando pulse \"Novo Xogo\"", "Actualización", JOptionPane.INFORMATION_MESSAGE, null);
                    dispose();
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(null, "Por favor, introduza ben os datos", "Erro", JOptionPane.INFORMATION_MESSAGE, null);
                }
            });

            //Pechamos a xanela
            botonCancelar.addActionListener( (ActionEvent e) -> {
                dispose();
            });

            setSize(600, 200);
            setResizable(false);
            setLocationRelativeTo(null);
            getContentPane().setBackground(new Color(25, 178, 172));
            setVisible(true);
        }

        /**
         * Colocamos e anhadimos os elementos
         */
        private void crearLayout ()
        {
            setLayout(null);

            this.labelTamanoMatriz.setBounds(10, 10, 400, 30);
            this.labelNumeroRondas.setBounds(10, 40, 400, 30);
            this.labelRondasCambio.setBounds(10, 70, 400, 30);
            this.labelPorcentaxe.setBounds(10, 100, 400, 30);

            this.areaTamanoMatriz.setBounds(410, 15, 180, 20);
            this.areaNumeroRondas.setBounds(410, 45, 180, 20);
            this.areaRondasCambio.setBounds(410, 75, 180, 20);
            this.areaPorcentaxe.setBounds(410, 105, 180, 20);

            this.botonAceptar.setBounds(125, 135, 150, 30);
            this.botonCancelar.setBounds(350, 135, 150, 30);

            for (JLabel label : this.labels)
                add(label);
            for (JTextArea text : this.textAreas)
                add(text);
            for (JButton boton : this.botons)
                add(boton);
        }
    }
}