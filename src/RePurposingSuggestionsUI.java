import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class RePurposingSuggestionsUI{
    private JFrame frame;
    private JButton nextButton;
    private JButton previousButton;
    private JButton searchButton;
    private JTextField searchField;
    private JTextArea suggestionsArea;

    private RePurposingSuggestionsController controller;


    public RePurposingSuggestionsUI(RePurposingSuggestionsController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Repurposing Suggestions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        suggestionsArea = new JTextArea(20, 50);
        suggestionsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(suggestionsArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showPreviousSuggestion();
            }
        });
        buttonPanel.add(previousButton);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showNextSuggestion();
            }
        });
        buttonPanel.add(nextButton);

        searchField = new JTextField(20);
        buttonPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                controller.searchSuggestions(keyword);
            }
        });
        buttonPanel.add(searchButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

//    public RePurposingSuggestionsUI() {
//        // Initialize UI components and layout
//        frame = new JFrame("Repurposing Suggestions");
//        // Create and configure UI components
//        // Add components to frame
//        // Set layout
//        // Add action listeners
//
//        public void display() {
//            frame.pack();
//            frame.setVisible(true);
//        }
//
//        // Methods to update UI elements based on model changes
//
//        public void addNextButtonListener(ActionListener listener) {
//            nextButton.addActionListener(listener);
//        }
//
//        // Other methods for adding listeners and updating UI elements
//
//
//    }

    public void displaySuggestions(List<RePurposingSuggestion> suggestions) {
        suggestionsArea.setText("");
        for (RePurposingSuggestion suggestion : suggestions) {
            suggestionsArea.append(suggestion.toString() + "\n");
        }
    }

    public void addNextButtonListener(RePurposingSuggestionsController.NextButtonListener nextButtonListener) {
    }
}