package main.java.memoranda.ui.htmleditor;
import java.awt.event.ActionEvent;

//TASK 2-1 SMELL WITHIN A CLASS

import javax.swing.AbstractAction;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

class ParaBreakAction extends AbstractAction  {
    public HTMLEditorPane editor = new HTMLEditorPane("");
    public HTMLDocument document = null;
    public HTMLEditorKit editorKit = new HTMLEditorKit();
        ParaBreakAction() {
            super("ParaBreakAction");
        }
        
        void removeIfEmpty(Element elem) {
            if (elem.getEndOffset() - elem.getStartOffset() < 2) {
                try {
                    document.remove(elem.getStartOffset(), elem.getEndOffset());
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            }
        }


        public void actionPerformed(ActionEvent e) {

            Element elem =
                document.getParagraphElement(editor.getCaretPosition());
            String elName = elem.getName().toUpperCase();
            String parentname = elem.getParentElement().getName();
            HTML.Tag parentTag = HTML.getTag(parentname);
            if (parentname.toUpperCase().equals("P-IMPLIED"))
                parentTag = HTML.Tag.IMPLIED;
            if (parentname.toLowerCase().equals("li")) {
                // HTML.Tag listTag =
                // HTML.getTag(elem.getParentElement().getParentElement().getName());
                if (elem.getEndOffset() - elem.getStartOffset() > 1) {
                    try {
                        document.insertAfterEnd(
                            elem.getParentElement(),
                            "<li></li>");
                        editor.setCaretPosition(
                            elem.getParentElement().getEndOffset());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    /*
                     * HTMLEditorKit.InsertHTMLTextAction liAction = new
                     * HTMLEditorKit.InsertHTMLTextAction("insertLI", " <li>
                     * </li> ", parentTag, HTML.Tag.LI);
                     */
                } else {
                    try {
                        document.remove(editor.getCaretPosition(), 1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Element listParentElement =
                        elem
                            .getParentElement()
                            .getParentElement()
                            .getParentElement();
                    HTML.Tag listParentTag =
                        HTML.getTag(listParentElement.getName());
                    String listParentTagName = listParentTag.toString();
                    if (listParentTagName.toLowerCase().equals("li")) {
                        Element listAncEl =
                            listParentElement.getParentElement();
                        try {
                            editorKit.insertHTML(
                                document,
                                listAncEl.getEndOffset(),
                                "<li><p></p></li>",
                                3,
                                0,
                                HTML.Tag.LI);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        HTMLEditorKit.InsertHTMLTextAction pAction =
                            new HTMLEditorKit.InsertHTMLTextAction(
                                "insertP",
                                "<p></p>",
                                listParentTag,
                                HTML.Tag.P);
                        pAction.actionPerformed(e);
                    }
                }
            } else if (
                (elName.equals("PRE"))
                    || (elName.equals("ADDRESS"))
                    || (elName.equals("BLOCKQUOTE"))) {
                if (editor.getCaretPosition() > 0)
                    removeIfEmpty(
                        document.getParagraphElement(
                            editor.getCaretPosition() - 1));
                HTMLEditorKit.InsertHTMLTextAction pAction =
                    new HTMLEditorKit.InsertHTMLTextAction(
                        "insertP",
                        "<p></p>",
                        parentTag,
                        HTML.Tag.P);
                System.out.println("PRE");
                pAction.actionPerformed(e);
            } else if (elName.equals("P-IMPLIED")) {
                /*
                 * HTML.Tag sParentTag =
                 * HTML.getTag(elem.getParentElement().getParentElement().getName());
                 * if (editor.getCaretPosition() > 0)
                 * removeIfEmpty(document.getParagraphElement(editor.getCaretPosition() -
                 * 1)); HTMLEditorKit.InsertHTMLTextAction pAction = new
                 * HTMLEditorKit.InsertHTMLTextAction("insertP", " <p></p> ",
                 * sParentTag, HTML.Tag.P);
                 * System.out.println(sParentTag.toString());
                 */
                try {
                    System.out.println("IMPLIED");
                    document.insertAfterEnd(elem.getParentElement(), "<p></p>");
                    editor.setCaretPosition(
                        elem.getParentElement().getEndOffset());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                //removeIfEmpty(editor.getStyledDocument().getParagraphElement(editor.getCaretPosition()-1));
                /*
                 * HTMLEditorKit.InsertHTMLTextAction pAction = new
                 * HTMLEditorKit.InsertHTMLTextAction("insertP"," <p></p> ",
                 * HTML.Tag.BODY, HTML.Tag.P);
                 */

                //HTMLEditorKit.InsertBreakAction iba = new
                // HTMLEditorKit.InsertBreakAction();
                //iba.actionPerformed(e);
                editor.replaceSelection("\n");
                editorKit.getInputAttributes().removeAttribute(
                    HTML.Attribute.ID);
                editorKit.getInputAttributes().removeAttribute(
                    HTML.Attribute.CLASS);
            }
            //System.out.println(e.getWhen());
        }
    }