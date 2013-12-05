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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class File extends java.io.File {

    /**
     * Required
     */
    private static final long serialVersionUID = -756962356996164247L;

    private static String lastSlash( String absolutePath ) {

        if( absolutePath != null && ( absolutePath.endsWith( "/" ) || absolutePath.endsWith( "\\" ) ) ) {
            return absolutePath.substring( 0, Math.max( absolutePath.lastIndexOf( "\\" ), absolutePath.lastIndexOf( "/" ) ) );
        }
        else {
            return absolutePath;
        }
    }

    public File( String parent, String path ) {

        super( lastSlash( parent ), path );
    }

    public File( String path ) {

        super( path );
    }

    public File( File parent, String name ) {

        this( parent.getAbsolutePath( ), name );
    }

    public static boolean copyTo( java.io.File origin, java.io.File destiny ) {

        boolean copied = true;
        try {
            InputStream in = new FileInputStream( origin );
            if( destiny.getParentFile( ) != null && !destiny.getParentFile( ).exists( ) ) {
                destiny.getParentFile( ).mkdirs( );
            }
            if( !destiny.exists( ) )
                destiny.createNewFile( );
            OutputStream out = new FileOutputStream( destiny );

            // Transfer bytes from in to out
            byte[] buf = new byte[ 1024 ];
            int len;
            while( ( len = in.read( buf ) ) > 0 ) {
                out.write( buf, 0, len );
            }
            in.close( );
            out.close( );
        }
        catch( Exception e ) {
            copied = false;
            //Controller.getInstance().showErrorDialog( TextConstants.getText("Error.Title"), TextConstants.getText("Error.CopyingFiles", new String[]{origin.getAbsolutePath( ), destiny.getName( )}) );
        }

        return copied;

    }

    public boolean copyTo( java.io.File destiny ) {

        return copyTo( this, destiny );
    }

    public static boolean copyAllTo( java.io.File origin, java.io.File destiny ) {

        boolean copied = true;
        if( !origin.exists( ) ) {
            //System.out.println( "ORIGIN NO CREADO: "+origin.getAbsolutePath( ) );
            return false;
        }
        try {
            if( origin.isDirectory( ) ) {
                //boolean copiedAux2 = destiny.mkdirs( );
                //copied &= copiedAux2;
                //if (!copiedAux2){
                //System.out.println( "DIRECTORIO NO CREADO(MKDIRS): "+destiny.getAbsolutePath( ) );
                //}
                for( java.io.File childOrigin : origin.listFiles( ) ) {
                    if( childOrigin.isDirectory( ) ) {
                        File childDestiny = new File( destiny.getAbsolutePath( ), childOrigin.getName( ) + "/" );
                        //boolean copiedAux =childDestiny.mkdirs( ); 
                        //copied &= copiedAux;
                        //if (!copiedAux){
                        //System.out.println( "DIRECTORIO HIJO NO CREADO(MKDIRS): "+childDestiny.getAbsolutePath( ) );
                        //}
                        copied &= copyAllTo( childOrigin, childDestiny );
                    }
                    else {
                        File childDestiny = new File( destiny.getAbsolutePath( ), childOrigin.getName( ) );
                        boolean copiedAux = copyTo( childOrigin, childDestiny );
                        copied &= copiedAux;
                        if( !copiedAux ) {
                            //System.out.println( "ARCHIVO NO COPIADO: "+childOrigin.getAbsolutePath( ) + "--->"
                            //		+childDestiny.getAbsolutePath( ) );
                        }
                    }
                }
            }

        }
        catch( Exception e ) {
            copied = false;
            //System.out.println("QUE EXCEPCIÓN MÁS CHUNGA!");
            //Controller.getInstance().showErrorDialog( TextConstants.getText("Error.Title"), TextConstants.getText("Error.CopyingFiles", new String[]{origin.getAbsolutePath( ), destiny.getName( )}) );
        }

        return copied;
    }

    public boolean copyAllTo( java.io.File destiny ) {

        return copyAllTo( this, destiny );
    }

    public boolean create( ) {

        boolean created = false;
        if( getParentFile( ) != null && !this.getParentFile( ).exists( ) ) {
            getParentFile( ).mkdirs( );
        }
        if( !getName( ).contains( "." ) && !exists( ) ) {
            created = mkdirs( );
        }
        else if( !exists( ) ) {
            try {
                created = this.createNewFile( );
            }
            catch( IOException e ) {
            }
        }
        return created;
    }

    @Override
    public File[] listFiles( ) {

        create( );

        java.io.File[] files = super.listFiles( );
        File[] filesConverted = new File[ files.length ];
        for( int i = 0; i < files.length; i++ ) {
            filesConverted[i] = new File( files[i].getAbsolutePath( ) );
        }
        return filesConverted;
    }

    public File[] listFiles( FileFilter filter ) {

        create( );

        java.io.File[] files = super.listFiles( filter );
        File[] filesConverted = new File[ files.length ];
        for( int i = 0; i < files.length; i++ ) {
            filesConverted[i] = new File( files[i].getAbsolutePath( ) );
        }
        return filesConverted;
    }

    public boolean deleteAll( ) {

        boolean deleted = true;
        if( this.isDirectory( ) ) {
            for( File child : this.listFiles( ) ) {
                if( child.isDirectory( ) ) {
                    deleted &= child.deleteAll( );
                }
                else {
                    deleted &= child.delete( );
                }
            }
            deleted &= this.delete( );
        }
        return deleted;
    }

    ////////////////// ZIP AND JAR METHODS
    public static void zipDirectory( String temp, String zipFile ) {

        try {
            //create a ZipOutputStream to zip the data to 
            ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( zipFile ) );
            zipDir( temp, "", zos );

            zos.close( );
        }
        catch( Exception e ) {
            //handle exception 
        }
    }

    /*public static boolean putFilesInJar(String originPath, String[] addOriginPaths, String destinyPath, String[] addDestinyPaths){
     boolean done = true;
     
     File originFile = new File(originPath);
     File destinyFile = new File(destinyPath);
     File[] additionalOriginFiles = new File[addOriginPaths.length];
     for (int i=0; i<addOriginPaths.length; i++){
    	 additionalOriginFiles[i] = new File(addOriginPaths[i]);
     }
     
         try {
       	  	// 1) Copy the entries in the zip file to the destiny file 
             final int BUFFER = 2048;
             BufferedOutputStream dest = null;
             JarOutputStream jos = null;
             FileInputStream fis = new FileInputStream(originFile);
             CheckedInputStream checksum = new CheckedInputStream(fis, new Adler32());
             JarInputStream jis = new JarInputStream(new BufferedInputStream(checksum));
             JarEntry entry = null;
             while((entry = jis.getNextJarEntry()) != null) {
                //System.out.println("Extracting: " +entry);
                int count;
                byte data[] = new byte[BUFFER];
                if (!entry.isDirectory( )){
    	            // write the files to the disk
    	            FileOutputStream fos = new FileOutputStream(new File(destinyFile,entry.getName()));
    	            dest = new BufferedOutputStream(fos,BUFFER);
    	            jos = new JarOutputStream(dest);
    	            jos.putNextEntry( entry );
    	            while ((count = jis.read(data, 0, BUFFER)) != -1) {
    	               jos.write(data, 0, count);
    	            }
    	            jos.flush();
    	            jos.close();
                }
             }
             jis.close();
             
             // 2) Copy the additional files to the new compressed file
             for (int i=0; i<additionalOriginFiles.length; i++){
            	 File additionalFile = additionalOriginFiles[i];
            	 String additionalFileDest = addDestinyPaths[i];
            	 JarEntry newEntry = new JarEntry(additionalFileDest);
            	 // Input Stream
            	 FileInputStream ais = new FileInputStream(additionalFile);
            	 int count2;
            	 byte data2[] = new byte[BUFFER];
            	 
            	 // Output stream
            	 FileOutputStream fos = new FileOutputStream(new File(destinyFile,newEntry.getName()));
    	         dest = new BufferedOutputStream(fos,BUFFER);
    	         jos = new JarOutputStream(dest);
    	         
    	         //Write the file
    	         jos.putNextEntry( newEntry );
    	         while ((count2 = ais.read(data2, 0, BUFFER)) != -1) {
    	        	 jos.write(data2, 0, count2);
    	         }
    	         ais.close( );
             }
             //System.out.println("Checksum: "+checksum.getChecksum().getValue());
          } catch(Exception e) {
        	  done = false;
             e.printStackTrace();
          }
          return done;
    }*/

    public static void zipDir( String dirOrigen, String relPath, ZipOutputStream zos ) {

        try {
            java.io.File zipDir = new java.io.File( dirOrigen );
            String[] dirList = zipDir.list( );
            //byte[] readBuffer = new byte[2156];

            for( int i = 0; i < dirList.length; i++ ) {
                java.io.File f = new java.io.File( zipDir, dirList[i] );
                if( f.isDirectory( ) ) {
                    String filePath = f.getAbsolutePath( );

                    if( relPath != null && !relPath.equals( "" ) )
                        zipDir( filePath, relPath + "/" + f.getName( ), zos );
                    else
                        zipDir( filePath, f.getName( ), zos );
                    //loop again 
                    continue;
                }

                FileInputStream fis = new FileInputStream( f );

                // Take the path of the file relative to the dirOrigen
                String entryName = f.getName( );
                if( relPath != null && !relPath.equals( "" ) ) {
                    entryName = relPath + "/" + entryName;
                }

                ZipEntry anEntry = new ZipEntry( entryName );
                //anEntry.setSize( f.length( ) );
                //anEntry.setTime( f.lastModified( ) );
                zos.putNextEntry( anEntry );
                byte[] readBuffer = new byte[ 1024 ];
                int bytesIn = 0;
                while( ( bytesIn = fis.read( readBuffer ) ) != -1 ) {
                    zos.write( readBuffer, 0, bytesIn );
                }
                //close the Stream 
                fis.close( );
                zos.closeEntry( );
            }
        }
        catch( Exception e ) {
            //handle exception 
        }
    }

    public static void unzipDir( String zipFile, String destinyDir ) {

        try {
            final int BUFFER = 2048;
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream( zipFile );
            CheckedInputStream checksum = new CheckedInputStream( fis, new Adler32( ) );
            JarInputStream jis = new JarInputStream( new BufferedInputStream( checksum ) );
            JarEntry entry = null;
            while( ( entry = jis.getNextJarEntry( ) ) != null ) {
                //System.out.println("Extracting: " +entry);
                int count;
                byte data[] = new byte[ BUFFER ];
                // write the files to the disk
                File newFile = new File( destinyDir, entry.getName( ) );
                newFile.create( );
                if( !newFile.isDirectory( ) ) {
                    FileOutputStream fos = new FileOutputStream( newFile );
                    dest = new BufferedOutputStream( fos, BUFFER );
                    while( ( count = jis.read( data, 0, BUFFER ) ) != -1 ) {
                        dest.write( data, 0, count );
                    }
                    dest.flush( );
                    dest.close( );
                }
            }
            jis.close( );
            //System.out.println("Checksum: "+checksum.getChecksum().getValue());
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }
    }

    /**
     * Merges the contents of a zip file and a directory to a resultant zip
     * file. It is used when exporting (standalone and LO versions)
     * 
     * @param originZipFile
     *            Complete path of the origin zip file (source 1)
     * @param originDir
     *            Complete path of the origin dir (source 2)
     * @param destinyJarFile
     *            Complete path of the destiny zip file (product)
     */
    public static void mergeZipAndDirToJar( String originZipFile, String originDir, ZipOutputStream zos ) {
        try {
            FileInputStream fis = new FileInputStream( originZipFile );
            CheckedInputStream checksum = new CheckedInputStream( fis, new Adler32( ) );
            ZipInputStream zis = new ZipInputStream( new BufferedInputStream( checksum ) );
            ZipEntry entry = null;

            // Write the contents of the origin zip file to the destiny jar file
            while( ( entry = zis.getNextEntry( ) ) != null ) {
                //System.out.println("Extracting: " +entry);
                // write the files to the disk
                JarEntry newEntry = new JarEntry( entry.getName( ) );

                zos.putNextEntry( newEntry );
                byte[] readBuffer = new byte[ 1024 ];
                int bytesIn = 0;
                while( ( bytesIn = zis.read( readBuffer ) ) != -1 ) {
                    zos.write( readBuffer, 0, bytesIn );
                }
                //close the Stream
                zos.closeEntry( );
            }
            zis.close( );

            // Write the contents of the origin dir to the jar
            File.zipDir( originDir, "", zos );

        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }
    }

    public static void addJarContentsToZip(String library, ZipOutputStream zos) {
        try {
            FileInputStream fis = new FileInputStream( library );
            ZipInputStream zis = new ZipInputStream( new BufferedInputStream( fis/*checksum*/ ) );
            ZipEntry entry = null;

            while( ( entry = zis.getNextEntry( ) ) != null ) {
                if (!entry.getName( ).contains( "META-INF/" ) || entry.getName( ).contains( "services" )) {
                    try {
                        zos.putNextEntry( entry );
                        byte[] readBuffer = new byte[ 1024 ];
                        int bytesIn = 0;
                        while( ( bytesIn = zis.read( readBuffer ) ) != -1 ) {
                            zos.write( readBuffer, 0, bytesIn );
                        }
                        zos.closeEntry( );
                    } catch( ZipException e) {
                        if (!e.getMessage( ).contains( "duplicate entry" ))
                            ReportDialog.GenerateErrorReport( e, true, "Problem adding library: " + library );
                    }
                }
            }
            zis.close( );
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "Problem adding library: " + library );
        }
    }
    
    public static void addFileToZip(File file, String destiniy, ZipOutputStream zos) {
        try {
            FileInputStream fis = new FileInputStream( file );
            
            ZipEntry entry = new ZipEntry(destiniy);
            zos.putNextEntry( entry );
            byte[] readBuffer = new byte[1024];
            int bytesIn = 0;
            while((bytesIn = fis.read( readBuffer)) != -1) {
                zos.write( readBuffer, 0, bytesIn );
            }
            zos.closeEntry();
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }
    }

    
    
}
