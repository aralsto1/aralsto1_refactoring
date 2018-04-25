package main.java.memoranda;

import main.java.memoranda.interfaces.INoteList;
import main.java.memoranda.interfaces.IProject;
import main.java.memoranda.interfaces.IResourcesList;
import main.java.memoranda.interfaces.ITaskList;

/*$Id: ProjectListener.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public interface ProjectListener {
  void projectChange(IProject prj, INoteList nl, ITaskList tl, IResourcesList rl);
  void projectWasChanged();
}