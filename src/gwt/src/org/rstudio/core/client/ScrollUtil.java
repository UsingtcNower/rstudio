/*
 * ScrollUtil.java
 *
 * Copyright (C) 2009-14 by RStudio, Inc.
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.core.client;

import org.rstudio.core.client.widget.RStudioFrame;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Document;

public class ScrollUtil
{
   public static void setScrollPositionOnLoad(final RStudioFrame frame, 
                                              final int scrollPosition)
   {
      Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
         @Override
         public boolean execute()
         {
            if (frame.getIFrame() == null)
               return false;
            
            if (frame.getIFrame().getContentDocument() == null)
               return false;

            Document doc = frame.getIFrame().getContentDocument();
            String readyState = getDocumentReadyState(doc);
            if (readyState == null)
               return false;
            
            if (!readyState.equals("complete"))
               return true;

            // restore scroll position
            if (scrollPosition > 0)
               doc.setScrollTop(scrollPosition);

            return false;
         }
      }, 50);
   }

   private final native static String getDocumentReadyState(Document doc) /*-{
      return doc.readyState || null;
   }-*/;
   
}