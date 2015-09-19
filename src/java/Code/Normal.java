/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

class Normal
{
  float x;
  float y;
  float z;

  public void normalize()
  {
    double factor = Math.sqrt(Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D));
    this.x = ((float)(this.x / factor));
    this.y = ((float)(this.y / factor));
    this.z = ((float)(this.z / factor));
  }
}
