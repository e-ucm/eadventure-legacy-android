/*******************************************************************************
 * <e-Adventure> Mobile for Android(TM) is a port of the <e-Adventure> research project to 	the Android(TM) platform.
 *        
 *          Copyright 2009-2012 <e-UCM> research group.
 *        
 *          <e-UCM> is a research group of the Department of Software Engineering
 *           and Artificial Intelligence at the Complutense University of Madrid
 *           (School of Computer Science).
 *        
 *           C Profesor Jose Garcia Santesmases sn,
 *           28040 Madrid (Madrid), Spain.
 *       
 *           For more info please visit:  <http://e-adventure.e-ucm.es/android> or
 *           <http://www.e-ucm.es>
 *        
 *        	 *Android is a trademark of Google Inc.
 *       	
 *        ****************************************************************************
 *     	 This file is part of <e-Adventure> Mobile, version 1.0.
 *     
 *    	 Main contributors - Roberto Tornero
 *     
 *     	 Former contributors - Alvaro Villoria 
 *     						    Juan Manuel de las Cuevas
 *     						    Guillermo Martin 	
 *    
 *     	 Directors - Baltasar Fernandez Manjon
 *     				Eugenio Marchiori
 *     
 *         	 You can access a list of all the contributors to <e-Adventure> Mobile at:
 *                	http://e-adventure.e-ucm.es/contributors
 *        
 *        ****************************************************************************
 *             <e-Adventure> Mobile is free software: you can redistribute it and/or modify
 *            it under the terms of the GNU Lesser General Public License as published by
 *            the Free Software Foundation, either version 3 of the License, or
 *            (at your option) any later version.
 *        
 *            <e-Adventure> Mobile is distributed in the hope that it will be useful,
 *            but WITHOUT ANY WARRANTY; without even the implied warranty of
 *            MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *            GNU Lesser General Public License for more details.
 *        
 *            See <http://www.gnu.org/licenses/>
 ******************************************************************************/
package es.eucm.eadandroid.common.auxiliar;

/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, available at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/

public interface SpecialAssetPaths {

    /**
     * Asset path for the empty image. For use only in assets.
     */
    public static final String ASSET_EMPTY_IMAGE = "assets/special/EmptyImage.png";

    /**
     * Asset path for the empty icon. For use only in assets.
     */
    public static final String ASSET_EMPTY_ICON = "assets/special/EmptyIcon.png";

    /**
     * Asset path for the empty animation. For use only in assets.
     */
    public static final String ASSET_EMPTY_ANIMATION = "assets/special/EmptyAnimation";

    /**
     * Asset path for the default book background image. For use only in assets.
     */
    public static final String ASSET_DEFAULT_BOOK_IMAGE = "assets/special/DefaultBook.jpg";
    
    /**
     * Asset path for the default book normal arrow image. For use only in assets.
     */
    public static final String ASSET_DEFAULT_ARROW_NORMAL = "assets/special/DefaultLeftNormalArrow.png";
    
    /**
     * Asset path for the default book over arrow image. For use only in assets.
     */
    public static final String ASSET_DEFAULT_ARROW_OVER = "assets/special/DefaultLeftOverArrow.png";
    
    /**
     * Default file for the empty image.
     */
    public static final String FILE_EMPTY_IMAGE = "img/assets/EmptyImage.png";

    /**
     * Default file for the empty icon.
     */
    public static final String FILE_EMPTY_ICON = "img/assets/EmptyIcon.png";

    /**
     * Default file for the empty animation.
     */
    public static final String FILE_EMPTY_ANIMATION = "img/assets/EmptyAnimation_01.png";

    /**
     * Default file for the default book background image.
     */
    public static final String FILE_DEFAULT_BOOK_IMAGE = "img/assets/DefaultBook.jpg";
    
    /**
     * Default file for the book normal arrow image.
     */
    public static final String FILE_DEFAULT_ARROW_NORMAL = "img/assets/DefaultLeftNormalArrow.png";
    
    /**
     * Default file for the book over arrow image.
     */
    public static final String FILE_DEFAULT_ARROW_OVER = "img/assets/DefaultLeftOverArrow.png";
    
}
