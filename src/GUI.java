import Generators.BSTArrayGenerator;
import Generators.BSTElementRemoval;
import Generators.HeapifyArray;
import Generators.HeapSort;
import Generators.AVLTree;
import Generators.AVLRemoveTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame {
    private JPanel MainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField tipud;
    private JTextField raskus;
    private JTextPane generatedArrays;
    private JButton genereeriButton;
    private JButton generate_BST_removals;
    private JTextField difficulty_BST_removal;
    private JTextArea BST_removals_response;
    private JTextField removals_BST_removals;
    private JTextField nodes_BST_removal;
    private JLabel labelvärk;
    private JLabel raskusparameeter;
    private JButton array_to_BST_generate;
    private JTextField array_to_BST_nodes;
    private JTextField array_to_BST_difficulty;
    private JTextArea array_to_BST_response;
    private JButton heap_sort_generate;
    private JTextField heap_sort_nodes;
    private JTextField heap_sort_difficutly;
    private JTextArea heap_sort_response;
    private JButton generateButton;
    private JTextField avl_additions_additions;
    private JTextField avl_additions_difficulty;
    private JTextArea avl_additions_response;
    private JTextField avl_additions_nodes;
    private JButton AVL_remove_button;
    private JTextField nodes_AVL_remove;
    private JTextField AVL_remove_difficulty;
    private JTextArea AVL_remove_response;
    private JTextField AVL_remove_removals;

    public GUI (){
        setContentPane(MainPanel);
        setTitle("GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        genereeriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent click) {
                List<List<Integer>> answer = HeapSort.execute(Integer.parseInt(tipud.getText()), Integer.parseInt(raskus.getText()));
                StringBuilder response = new StringBuilder();
                for (int i = 0; i < answer.size(); i++) {
                    response.append(answer.get(i)).append(" \n");
                    System.out.println("Element at index " + i + " is " + answer.get(i));
                }
                generatedArrays.setText(response.toString());
            }
        });
        generate_BST_removals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<List<Integer>>> answer = BSTElementRemoval.execute(Integer.parseInt(nodes_BST_removal.getText()),Integer.parseInt(removals_BST_removals.getText()),Integer.parseInt(difficulty_BST_removal.getText()));
                StringBuilder response = new StringBuilder();
                for (int i = 0; i < answer.size(); i++) {
                    response.append("Kahendotsimispuu järjend: ").append(answer.get(i).get(1)).append("      Eemaldatavad elemendid: ").append(answer.get(i).get(0)).append(" \n");
                    System.out.println("Element at index " + i + " is " + answer.get(i).get(1));
                }
                BST_removals_response.setText(response.toString());
            }
        });
        array_to_BST_generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<Integer>> answer = BSTArrayGenerator.execute(Integer.parseInt(array_to_BST_nodes.getText()), Integer.parseInt(array_to_BST_difficulty.getText()));
                StringBuilder response = new StringBuilder();
                for (int i = 0; i < answer.size(); i++) {
                    response.append(answer.get(i)).append(" \n");
                }
                array_to_BST_response.setText(response.toString());
            }
        });
        heap_sort_generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<Integer>> answer = HeapSort.execute(Integer.parseInt(heap_sort_nodes.getText()), Integer.parseInt(heap_sort_difficutly.getText()));
                StringBuilder response = new StringBuilder();
                for (int i = 0; i < answer.size(); i++) {
                    response.append(answer.get(i)).append(" \n");
                    System.out.println("Element at index " + i + " is " + answer.get(i));
                }
                heap_sort_response.setText(response.toString());
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<List<Integer>>> answer = AVLTree.execute(Integer.parseInt(avl_additions_nodes.getText()), Integer.parseInt(avl_additions_difficulty.getText()));
                StringBuilder response = new StringBuilder();
                for (int i = 0; i < answer.size(); i++) {
                    response.append("AVL-puu järjend: " + answer.get(i).get(1) + "      Lisatavad elemendid: " + answer.get(i).get(0)).append(" \n");
                }
                avl_additions_response.setText(response.toString());
            }
        });
        AVL_remove_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<List<Integer>>> answer = (List<List<List<Integer>>>) AVLRemoveTree.execute(Integer.parseInt(nodes_AVL_remove.getText()),Integer.parseInt(AVL_remove_removals.getText()),Integer.parseInt(AVL_remove_difficulty.getText()));
                StringBuilder response = new StringBuilder();
                for (int i = 0; i < answer.size(); i++) {
                    response.append("AVL-puu järjend: ").append(answer.get(i).get(0)).append("      Eemaldatavad elemendid: ").append(answer.get(i).get(1)).append(" \n");
                    System.out.println("Element at index " + i + " is " + answer.get(i).get(1));
                }
                AVL_remove_response.setText(response.toString());
            }
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}
